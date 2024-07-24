package club.someoneice.crystaldream.common.block

import club.someoneice.crystaldream.api.AbstractBlockWithTileItem
import club.someoneice.crystaldream.common.tile.TileAltar
import club.someoneice.crystaldream.core.init.ModTiles
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape


class BlockAltar : AbstractBlockWithTileItem() {
    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity = TileAltar(pPos, pState)
    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.ENTITYBLOCK_ANIMATED
    override fun codec(): MapCodec<out BaseEntityBlock> = simpleCodec { BlockAltar() }

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