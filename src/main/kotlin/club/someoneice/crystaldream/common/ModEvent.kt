package club.someoneice.crystaldream.common

import club.someoneice.crystaldream.core.CrystalDream
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent

@EventBusSubscriber(modid = CrystalDream.MODID)
object ModEvent {
    @SubscribeEvent
    fun burnTimeEvent(event: FurnaceFuelBurnTimeEvent) {
        event.burnTime
    }
}