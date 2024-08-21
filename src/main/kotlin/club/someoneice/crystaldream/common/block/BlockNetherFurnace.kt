package club.someoneice.crystaldream.common.block

import club.someoneice.crystaldream.common.tile.TileNetherFurnace
import club.someoneice.crystaldream.core.init.ModTiles
import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class BlockNetherFurnace: BaseEntityBlock(Properties.ofFullCopy(Blocks.STONE)) {
    override fun newBlockEntity(p0: BlockPos, p1: BlockState) = TileNetherFurnace(p0, p1)
    override fun codec(): MapCodec<out BaseEntityBlock> = simpleCodec { BlockNetherFurnace() }

    override fun useWithoutItem(state: BlockState, world: Level, pos: BlockPos, player: Player, hitResult: BlockHitResult): InteractionResult {
        val pass = super.useWithoutItem(state, world, pos, player, hitResult)
        if (world.isClientSide()) {
            return pass
        }

        val tile = world.getBlockEntity(pos)
        if (tile !is TileNetherFurnace) {
            return pass
        }

        player.openMenu(tile)
        return InteractionResult.SUCCESS
    }

    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? =
        createTickerHelper(blockEntityType, ModTiles.NETHER_FURNACE, TileNetherFurnace::tick)
}