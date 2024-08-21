package club.someoneice.crystaldream.common.item

import club.someoneice.crystaldream.api.INetherFurnaceFuelItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

class ItemGhost: Item(Properties()), INetherFurnaceFuelItem {
    override fun getBurnTime(item: ItemStack): Int = 20 * 60
}