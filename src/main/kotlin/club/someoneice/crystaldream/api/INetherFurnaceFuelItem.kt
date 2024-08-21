package club.someoneice.crystaldream.api

import net.minecraft.world.item.ItemStack

interface INetherFurnaceFuelItem {
    fun getBurnTime(item: ItemStack): Int = 3600
}