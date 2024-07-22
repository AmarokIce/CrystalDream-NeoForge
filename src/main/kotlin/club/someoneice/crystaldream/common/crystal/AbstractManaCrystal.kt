package club.someoneice.crystaldream.common.crystal

import club.someoneice.crystaldream.core.init.ModCapabilities
import net.minecraft.util.Mth
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

abstract class AbstractManaCrystal() : Item(Properties().stacksTo(1)) {
    abstract fun getMaxMana(): Int

    fun addMana(item: ItemStack, mana:Int) {
        item.getCapability(ModCapabilities.CRYSTAL_MANA)?.let {
            it.setMana(Mth.clamp(it.getMana() + mana, 0, this.getMaxMana()))
        }
    }
}