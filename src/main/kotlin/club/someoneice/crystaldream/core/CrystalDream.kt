package club.someoneice.crystaldream.core

import club.someoneice.crystaldream.common.capability.CapabilityCrystalMana
import club.someoneice.crystaldream.common.item.crystal.AbstractManaCrystal
import club.someoneice.crystaldream.core.init.ModCapabilities
import club.someoneice.crystaldream.core.init.ModRegister
import net.minecraft.core.registries.BuiltInRegistries
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import org.apache.logging.log4j.LogManager
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(CrystalDream.MODID)
object CrystalDream {
    const val MODID = "crystaldream"
    val LOGGER = LogManager.getLogger(MODID)

    init {
        ModRegister.register()

        MOD_BUS.addListener(this::onCapabilityRegister)
    }

    private fun onCapabilityRegister(event: RegisterCapabilitiesEvent) {
        BuiltInRegistries.ITEM.forEach {
            if (it !is AbstractManaCrystal) return@forEach
            event.registerItem(ModCapabilities.CRYSTAL_MANA, { item, _ ->
                LOGGER.info("Register capability crystalmana for item {} ", item.displayName)
                CapabilityCrystalMana(item)
            }, it)
        }
    }
}
