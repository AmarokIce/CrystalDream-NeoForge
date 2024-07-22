package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.api.ICapabilityCrystalMana
import club.someoneice.crystaldream.util.createModPath
import net.neoforged.neoforge.capabilities.ItemCapability

object ModCapabilities {
    val CRYSTAL_MANA: ItemCapability<ICapabilityCrystalMana, Void> =
        ItemCapability.createVoid(createModPath("crystal_mana"), ICapabilityCrystalMana::class.java)
}