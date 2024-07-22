package club.someoneice.crystaldream.core

import club.someoneice.crystaldream.client.ClientMain
import club.someoneice.crystaldream.core.init.ModItems
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import org.apache.logging.log4j.LogManager
import org.jline.utils.Log
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.neoforge.forge.runForDist

@Mod(CrystalDream.MODID)
object CrystalDream {
    const val MODID = "crystaldream"
    val LOG = LogManager.getLogger(MODID)

    init {
        ModItems.ITEMS.register(MOD_BUS)

        runForDist(
            clientTarget = { MOD_BUS.addListener(CrystalDream::onClientSetup) },
            serverTarget = { MOD_BUS.addListener(CrystalDream::onServerSetup) }
        )
    }

    private fun onClientSetup(event: FMLClientSetupEvent) {
        Log.debug("CrystalDream client main start!")
        event.enqueueWork {
            ClientMain.clientRun(event)
        }
        Log.debug("CrystalDream client main finish!")
    }

    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        Log.debug("CrystalDream server main start!")
        event.enqueueWork {
            ServerMain.serverRun(event)
        }
        Log.debug("CrystalDream server main start!")
    }

    @SubscribeEvent
    fun onCommonSetup(event: FMLCommonSetupEvent) {
    }
}
