package club.someoneice.crystaldream.common.block

import club.someoneice.crystaldream.api.AbstractBlockWithTileItem
import club.someoneice.crystaldream.common.tile.TileMount
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

class BlockMount : AbstractBlockWithTileItem() {
    override fun codec(): MapCodec<BlockMount> = simpleCodec { BlockMount() }

    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity = TileMount(pPos, pState)

    override fun getShape(
        pState: BlockState,
        pLevel: BlockGetter,
        pPos: BlockPos,
        pContext: CollisionContext
    ): VoxelShape {
        var shape: VoxelShape = Shapes.empty()

        shape = Shapes.join(shape, Shapes.box(0.1875, 0.0, 0.1875, 0.8125, 0.1875, 0.8125), BooleanOp.OR)
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.1875, 0.3125, 0.6875, 0.6875, 0.6875), BooleanOp.OR)
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.6875, 0.1875, 0.8125, 0.875, 0.8125), BooleanOp.OR)

        return shape
    }
}