package club.someoneice.crystaldream.core

import club.someoneice.crystaldream.api.AbstractManaCrystal
import club.someoneice.crystaldream.common.capability.CapabilityCrystalMana
import club.someoneice.crystaldream.core.init.ModCapabilities
import club.someoneice.crystaldream.core.init.ModRecipes
import club.someoneice.crystaldream.core.init.ModRegister
import club.someoneice.crystaldream.core.init.ModTiles
import net.minecraft.core.registries.BuiltInRegistries
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.capabilities.Capabilities
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
        MOD_BUS.addListener(this::onCommonInit)
    }


    private fun onCommonInit(event: FMLCommonSetupEvent) {
        ModRecipes.init()
    }

    private fun onCapabilityRegister(event: RegisterCapabilitiesEvent) {
        BuiltInRegistries.ITEM.forEach {
            if (it !is AbstractManaCrystal) return@forEach
            event.registerItem(ModCapabilities.CRYSTAL_MANA, { item, _ ->
                LOGGER.info("Register capability crystalmana for item {} ", item.displayName)
                CapabilityCrystalMana(item)
            }, it)
        }

        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModTiles.NETHER_FURNACE) { tile, _ -> tile.handler }
    }
}
