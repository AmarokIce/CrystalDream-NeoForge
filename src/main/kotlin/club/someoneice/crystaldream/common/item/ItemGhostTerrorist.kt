package club.someoneice.crystaldream.common.item

import club.someoneice.crystaldream.core.init.ModItems
import club.someoneice.crystaldream.util.asEntityAndSpawn
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Blocks

class ItemGhostTerrorist : Item(Properties().stacksTo(1)) {
    override fun onItemUseFirst(stack: ItemStack, context: UseOnContext): InteractionResult {
        val world = context.level
        val pos = context.clickedPos
        val block = world.getBlockState(pos)

        when {
            block.`is`(Blocks.SOUL_SAND) -> {
                world.setBlock(pos, Blocks.SAND.defaultBlockState(), 3)
            }

            block.`is`(Blocks.SOUL_SOIL) -> {
                world.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3)
            }

            else -> return InteractionResult.CONSUME
        }

        ModItems.SOUL.defaultInstance
            .asEntityAndSpawn(world, pos.x.toDouble() + 0.5, pos.y.toDouble() + 1, pos.z.toDouble() + 0.5)
        world.playSound(context.player, pos, SoundEvents.SOUL_SAND_BREAK, SoundSource.BLOCKS)

        return InteractionResult.SUCCESS
    }
}
