package club.someoneice.crystaldream.core

import club.someoneice.crystaldream.api.capability.CapabilityCrystalMana
import club.someoneice.crystaldream.common.crystal.AbstractManaCrystal
import club.someoneice.crystaldream.core.init.ModCapabilities
import club.someoneice.crystaldream.core.init.ModItems
import net.minecraft.core.registries.BuiltInRegistries
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import org.apache.logging.log4j.LogManager
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(CrystalDream.MODID)
object CrystalDream {
    const val MODID = "crystaldream"
    val LOG = LogManager.getLogger(MODID)

    init {
        ModItems.ITEMS.register(MOD_BUS)
        MOD_BUS.addListener(this::onCapabilityRegister)
    }

    @SubscribeEvent
    fun onCommonSetup(event: FMLCommonSetupEvent) {
    }

    private fun onCapabilityRegister(event: RegisterCapabilitiesEvent) {
        BuiltInRegistries.ITEM.forEach {
            if (it !is AbstractManaCrystal) return@forEach
            event.registerItem(ModCapabilities.CRYSTAL_MANA, { item, _ ->
                CapabilityCrystalMana(item)
            }, it)
        }
    }
}
