package club.someoneice.crystaldream.core.init

import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS

object ModRegister {
    @JvmField val MOD_ITEMS = ModItems.ITEMS
    @JvmField val MOD_TABS = ModGroups.GROUPS


    @JvmName("# register")
    internal fun register() {
        MOD_ITEMS.register(MOD_BUS)
        MOD_TABS.register(MOD_BUS)
    }
}