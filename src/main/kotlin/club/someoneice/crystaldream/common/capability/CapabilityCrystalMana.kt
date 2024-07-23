package club.someoneice.crystaldream.common.capability

import club.someoneice.crystaldream.api.ICapabilityCrystalMana
import net.minecraft.core.component.DataComponents
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.CustomData
import java.util.concurrent.atomic.AtomicInteger

class CapabilityCrystalMana(private val item: ItemStack) : ICapabilityCrystalMana {
    override fun getMana(): Int {
        val data = readData()
        val value = AtomicInteger()
        data.update {
            value.set(it.getInt("crystal_mana_value"))
        }
        return value.get()
    }

    override fun setMana(mana: Int) {
        val data = readData()
        data.update {
            it.putInt("crystal_mana_value", mana)
        }
    }

    private fun readData() = item.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
}