package club.someoneice.crystaldream.common.item

import club.someoneice.crystaldream.api.INetherFurnaceFuelItem
import club.someoneice.crystaldream.util.createModInfo
import net.minecraft.ChatFormatting
import net.minecraft.network.chat.Component
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

class BaseNetherFurnaceFuel(properties: Properties, private val tick: Int): Item(properties), INetherFurnaceFuelItem {
    constructor(tick: Int): this(Properties(), tick)

    override fun getBurnTime(item: ItemStack): Int = tick
    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        tooltipComponents.add(Component.translatable(createModInfo("soul_fuel", "info")).append(" ${tick / 20} s").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC))
    }
}