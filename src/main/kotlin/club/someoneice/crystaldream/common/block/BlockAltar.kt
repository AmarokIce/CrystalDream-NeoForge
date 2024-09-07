package club.someoneice.crystaldream.common.block

import club.someoneice.crystaldream.api.AbstractBlockWithTileItem
import club.someoneice.crystaldream.common.tile.TileAltar
import club.someoneice.crystaldream.common.tile.TileMount
import club.someoneice.crystaldream.core.init.ModItems
import club.someoneice.crystaldream.core.init.ModRecipes
import club.someoneice.crystaldream.core.init.ModTiles
import club.someoneice.crystaldream.util.createBlockPosArrayWithRange
import club.someoneice.crystaldream.util.spawnParticlesAs
import com.mojang.serialization.MapCodec
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.CandleBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape


class BlockAltar : AbstractBlockWithTileItem() {
    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity = TileAltar(pPos, pState)
    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.ENTITYBLOCK_ANIMATED
    override fun codec(): MapCodec<out BaseEntityBlock> = simpleCodec { BlockAltar() }

    override fun useItemOn(stack: ItemStack, state: BlockState, world: Level, pos: BlockPos, player: Player, hand: InteractionHand, hitResult: BlockHitResult): ItemInteractionResult {
        if (!stack.`is`(ModItems.SHEPHERD_STAFF))
            return super.useItemOn(stack, state, world, pos, player, hand, hitResult)

        val tile = world.getBlockEntity(pos)
        if (tile !is TileAltar) {
            return ItemInteractionResult.FAIL
        }

        val candlePos = arrayOf(
            pos.offset(3, 2, 3),
            pos.offset(-3, 2, -3),
            pos.offset(-3, 2, 3),
            pos.offset(3, 2, -3)
        )

        candlePos.forEach {
            val block = world.getBlockState(it)
            if (block.isAir || !block.`is`(Blocks.PURPLE_CANDLE)) {
                return@useItemOn ItemInteractionResult.FAIL
            }

            if (!block.getValue(CandleBlock.LIT)) {
                return@useItemOn ItemInteractionResult.FAIL
            }
        }

        val tilePos = createBlockPosArrayWithRange(2, pos)

        val items = ArrayList<ItemStack>()

        tilePos.forEach {
            val tileMount = world.getBlockEntity(it)
            if (tileMount !is TileMount) {
                return@useItemOn ItemInteractionResult.FAIL
            }

            items.add(tileMount.getItem())
        }

        val catalyst = tile.getItem()

        val recipe = ModRecipes.RECIPE_OF_SACRIFICE.firstOrNull { it.findMatch(NonNullList.copyOf(items), catalyst) }
            ?: return ItemInteractionResult.FAIL

        tile.cleanItem()
        tilePos.forEach {
            val tileMount = world.getBlockEntity(it) as TileMount
            tileMount.cleanItem()

            if (world.isClientSide) {
                spawnParticlesAs(world as ClientLevel, it, pos, ParticleTypes.PORTAL, 10)
            }
        }

        tile.setItem(recipe.output.copy())

        return ItemInteractionResult.SUCCESS
    }

    override fun <T : BlockEntity?> getTicker(
        world: Level,
        state: BlockState,
        pBlockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? = createTickerHelper(pBlockEntityType, ModTiles.ALTAR_CORE, TileAltar::tick)

    override fun getShape(
        pState: BlockState,
        pLevel: BlockGetter,
        pPos: BlockPos,
        pContext: CollisionContext
    ): VoxelShape = box(0.0, 0.0, 0.0, 16.0, 32.0, 16.0)

    override fun isOcclusionShapeFullBlock(pState: BlockState, pLevel: BlockGetter, pPos: BlockPos): Boolean = false
}