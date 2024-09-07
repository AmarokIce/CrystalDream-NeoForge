package club.someoneice.crystaldream.common.block

import club.someoneice.crystaldream.common.tile.TileNetherFurnace
import club.someoneice.crystaldream.core.init.ModTiles
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.ParticleUtils
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.Vec3

class BlockNetherFurnace: BaseEntityBlock(Properties.ofFullCopy(Blocks.STONE)) {
    companion object {
        val FACING: DirectionProperty = HorizontalDirectionalBlock.FACING
        val LIT: BooleanProperty = BlockStateProperties.LIT
    }

    init {
        this.registerDefaultState(
            stateDefinition.any().setValue(AbstractFurnaceBlock.FACING, Direction.NORTH)
                .setValue(AbstractFurnaceBlock.LIT, false)
        )
    }

    override fun newBlockEntity(pos: BlockPos, state: BlockState) = TileNetherFurnace(pos, state)
    override fun codec(): MapCodec<out BaseEntityBlock> = simpleCodec { BlockNetherFurnace() }

    override fun useWithoutItem(state: BlockState, world: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
        val pass = super.useWithoutItem(state, world, pos, player, hitResult)
        if (world.isClientSide()) {
            return InteractionResult.SUCCESS
        }

        val tile = world.getBlockEntity(pos)
        if (tile !is TileNetherFurnace) {
            return pass
        }

        (player as ServerPlayer).openMenu(tile, pos)
        return InteractionResult.SUCCESS
    }

    override fun animateTick(state: BlockState, world: Level, pos: BlockPos, random: RandomSource) {
        if (!state.getValue(LIT)) {
            return
        }

        val x = pos.x.toDouble() + 0.5
        val y = pos.y.toDouble()
        val z = pos.z.toDouble() + 0.5

        if (random.nextDouble() < 0.1) {
            world.playLocalSound(x, y, z,
                SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0f, 1.0f, false)
        }

        ParticleUtils.spawnParticleOnFace(world, pos, Direction.UP, ParticleTypes.SMOKE, Vec3(0.0, 0.025, 0.0), 0.55)
        ParticleUtils.spawnParticleOnFace(world, pos, state.getValue(FACING), ParticleTypes.SOUL_FIRE_FLAME, Vec3(0.0, 0.025, 0.0), 0.55)
    }

    override fun hasAnalogOutputSignal(state: BlockState): Boolean = true
    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.MODEL

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState =
        defaultBlockState().setValue(AbstractFurnaceBlock.FACING, context.horizontalDirection.opposite)

    override fun getAnalogOutputSignal(blockState: BlockState, level: Level, pos: BlockPos): Int =
        AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos))

    override fun rotate(state: BlockState, rotation: Rotation): BlockState =
        state.setValue(AbstractFurnaceBlock.FACING, rotation.rotate(state.getValue(AbstractFurnaceBlock.FACING)))

    override fun mirror(state: BlockState, mirror: Mirror): BlockState =
        state.rotate(mirror.getRotation(state.getValue(AbstractFurnaceBlock.FACING)))


    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        builder.add(AbstractFurnaceBlock.FACING, AbstractFurnaceBlock.LIT)
    }

    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? =
        createTickerHelper(blockEntityType, ModTiles.NETHER_FURNACE, TileNetherFurnace::tick)
}