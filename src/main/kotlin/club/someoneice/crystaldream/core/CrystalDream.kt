package club.someoneice.crystaldream.core

import club.someoneice.crystaldream.common.capability.CapabilityCrystalMana
import club.someoneice.crystaldream.common.item.manacrystal.AbstractManaCrystal
import club.someoneice.crystaldream.core.init.ModCapabilities
import club.someoneice.crystaldream.core.init.ModRecipes
import club.someoneice.crystaldream.core.init.ModRegister
import club.someoneice.crystaldream.core.packet.PacketSendClientMessage
import net.minecraft.core.registries.BuiltInRegistries
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler
import org.apache.logging.log4j.LogManager
import thedarkcolour.kotlinforforge.neoforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

@Mod(CrystalDream.MODID)
object CrystalDream {
    const val MODID = "crystaldream"
    val LOGGER = LogManager.getLogger(MODID)

    init {
        ModRegister.register()

        MOD_BUS.addListener(this::onCommonInit)
        MOD_BUS.addListener(this::onCapabilityRegister)
        MOD_BUS.addListener(this::registerPayload)

        FORGE_BUS.addListener(ModRecipes::registerBrewingRecipes)
    }

    @SubscribeEvent
    private fun onCommonInit(event: FMLCommonSetupEvent) {
        ModRecipes.init()
    }

    @SubscribeEvent
    private fun onCapabilityRegister(event: RegisterCapabilitiesEvent) {
        BuiltInRegistries.ITEM.forEach {
            if (it !is AbstractManaCrystal) return@forEach
            event.registerItem(ModCapabilities.CRYSTAL_MANA, { item, _ ->
                LOGGER.info("Register capability crystalmana for item {} ", item.displayName)
                CapabilityCrystalMana(item)
            }, it)
        }
    }

    @SubscribeEvent
    private fun registerPayload(event: RegisterPayloadHandlersEvent) {
        val registrar = event.registrar("1")
        registrar.playToClient(PacketSendClientMessage.TYPE, PacketSendClientMessage.STREAM_CODEC, DirectionalPayloadHandler(
            PacketSendClientMessage::handleData,
            PacketSendClientMessage::handleData
        ))
    }
}
