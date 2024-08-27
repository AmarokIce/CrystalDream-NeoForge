package club.someoneice.crystaldream.common.item

import club.someoneice.crystaldream.util.createModInfo
import club.someoneice.crystaldream.util.sendClientDisplayMessage
import club.someoneice.crystaldream.util.updateNbt
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class ItemStableEnderPearl: Item(Properties().stacksTo(32)) {
    override fun use(world: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val item = player.getItemInHand(usedHand)

        item.updateNbt {
            if (player.isShiftKeyDown) {
                it.remove("pos")
                player.sendClientDisplayMessage(createModInfo("clean", "stable_ender_pearl"))
                return@updateNbt
            }

            if (it.contains("pos")) {
                val pos = BlockPos.of(it.getLong("pos"))
                player.teleportTo(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble())
                if (!player.isCreative) {
                    item.shrink(1)
                }
                return@updateNbt
            }

            val pos = player.onPos.above(1).asLong()
            it.putLong("pos", pos)
            player.sendClientDisplayMessage(createModInfo("success", "stable_ender_pearl"))
        }

        return InteractionResultHolder.success(item)
    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        if (stack.isEmpty) {
            return
        }

        stack.updateNbt {
            if (!it.contains("pos")) {
                return@updateNbt
            }

            val pos = BlockPos.of(it.getLong("pos"))
            tooltipComponents.add(Component.literal("x: ${pos.x}, y: ${pos.y}, z: ${pos.z}").withStyle(ChatFormatting.GRAY))
        }
    }
}