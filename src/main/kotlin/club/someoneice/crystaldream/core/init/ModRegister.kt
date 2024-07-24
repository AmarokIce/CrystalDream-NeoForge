package club.someoneice.crystaldream.core.init

import org.jetbrains.annotations.ApiStatus
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

object ModRegister {
    @JvmField val MOD_ITEMS = ModItems.ITEMS
    @JvmField val MOD_BLOCKS = ModBlocks.BLOCKS
    @JvmField val MOD_TABS = ModTabs.TABS

    @ApiStatus.Internal
    @JvmName("# register")
    internal fun register() {
        MOD_ITEMS.register(MOD_BUS)
        MOD_BLOCKS.register(MOD_BUS)
        MOD_TABS.register(MOD_BUS)
    }
}