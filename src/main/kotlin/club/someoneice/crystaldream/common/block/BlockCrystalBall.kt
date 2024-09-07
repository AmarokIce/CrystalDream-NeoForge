package club.someoneice.crystaldream.common.block

import club.someoneice.crystaldream.common.block.tile.TileCrystalBall
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.BooleanOp
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape


class BlockCrystalBall : BaseEntityBlock(Properties.ofFullCopy(Blocks.GLASS).noOcclusion()) {
    override fun newBlockEntity(p0: BlockPos, p1: BlockState) = TileCrystalBall(p0, p1)
    override fun codec(): MapCodec<out BaseEntityBlock> = simpleCodec { BlockCrystalBall() }
    override fun getRenderShape(state: BlockState) = RenderShape.ENTITYBLOCK_ANIMATED
    override fun getShape(
        pState: BlockState,
        pLevel: BlockGetter,
        pPos: BlockPos,
        pContext: CollisionContext
    ): VoxelShape {
        var shape: VoxelShape = Shapes.empty()
        shape = Shapes.join(shape, Shapes.box(0.3, 0.2, 0.3, 0.7, 0.6000000000000001, 0.7), BooleanOp.OR)
        shape = Shapes.join(shape, Shapes.box(0.275, 0.0, 0.275, 0.725, 0.045000000000000005, 0.725), BooleanOp.OR)
        return shape
    }
}