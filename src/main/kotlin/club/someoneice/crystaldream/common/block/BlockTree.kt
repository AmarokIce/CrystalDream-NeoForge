package club.someoneice.crystaldream.common.block

import club.someoneice.crystaldream.api.AbstractBlockWithTileItem
import club.someoneice.crystaldream.common.block.tile.TileTree
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class BlockTree : AbstractBlockWithTileItem(Properties.ofFullCopy(Blocks.OAK_PLANKS).noOcclusion()) {
    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity =
        TileTree(blockPos, blockState)

    override fun getRenderShape(pState: BlockState): RenderShape = RenderShape.MODEL
    override fun codec(): MapCodec<out BaseEntityBlock> = simpleCodec { BlockTree() }
}
