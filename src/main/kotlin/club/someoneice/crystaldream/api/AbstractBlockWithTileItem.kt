package club.someoneice.crystaldream.api

import club.someoneice.crystaldream.util.giveOrThrowOut
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

abstract class AbstractBlockWithTileItem(properties: Properties) : BaseEntityBlock(properties) {
    constructor() : this(Properties.of().noOcclusion().strength(3.5f))


    override fun spawnAfterBreak(
        pState: BlockState,
        pLevel: ServerLevel,
        pPos: BlockPos,
        pStack: ItemStack,
        pDropExperience: Boolean
    ) {
        super.spawnAfterBreak(pState, pLevel, pPos, pStack, pDropExperience)
        val list = ArrayList<ItemStack>()
        list.add(this.asItem().defaultInstance)
        pLevel.getBlockEntity(pPos)?.let { tile -> if (tile is IItemTile) list.add(tile.getItem()) }
    }

    override fun useItemOn(
        stack: ItemStack,
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hitResult: BlockHitResult
    ): ItemInteractionResult {
        super.useItemOn(stack, state, level, pos, player, hand, hitResult)
        if (hand == InteractionHand.OFF_HAND) {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION
        }

        val item = player.mainHandItem
        val tile = level.getBlockEntity(pos)
        if (tile !is IItemTile) {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION
        }

        if (player.isShiftKeyDown) {
            if (!item.isEmpty) return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION
            if (tile.getItem().isEmpty) return ItemInteractionResult.FAIL
            else player.giveOrThrowOut(tile.getAndCleanItem())
            return ItemInteractionResult.SUCCESS
        }

        if (item.isEmpty || !tile.getItem().isEmpty) {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION
        }
        val it = item.copy()
        it.count = 1
        tile.setItem(it)

        item.count--
        return ItemInteractionResult.SUCCESS
    }

    @Deprecated("Deprecated in Java", ReplaceWith("RenderShape.MODEL", "net.minecraft.world.level.block.RenderShape"))
    override fun getRenderShape(pState: BlockState): RenderShape {
        return RenderShape.MODEL
    }
}