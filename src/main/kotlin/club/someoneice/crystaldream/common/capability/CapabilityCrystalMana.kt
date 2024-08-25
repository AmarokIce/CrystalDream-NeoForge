package club.someoneice.crystaldream.common.capability

import club.someoneice.crystaldream.api.ICapabilityCrystalMana
import club.someoneice.crystaldream.util.updateNbt
import net.minecraft.world.item.ItemStack
import java.util.concurrent.atomic.AtomicInteger

class CapabilityCrystalMana(private val item: ItemStack) : ICapabilityCrystalMana {
    override fun getMana(): Int {
        val value = AtomicInteger()
        item.updateNbt {
            value.set(it.getInt("crystal_mana_value"))
        }
        return value.get()
    }

    override fun setMana(mana: Int) {
        item.updateNbt {
            it.putInt("crystal_mana_value", mana)
        }
    }
}