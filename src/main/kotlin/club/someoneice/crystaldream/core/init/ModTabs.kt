package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.core.CrystalDream
import com.google.common.base.Supplier
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object ModTabs {
    internal val TABS: DeferredRegister<CreativeModeTab> = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, CrystalDream.MODID)

    @Suppress("Unused")
    val GROUP_MAIN: CreativeModeTab by TABS.register("main", object: Supplier<CreativeModeTab> {
        override fun get() = CreativeModeTab.builder().noScrollBar()
            .title(Component.literal("crystaldream.tab"))
            .icon { ModItems.SOUL_CRYSTAL.defaultInstance }
            .displayItems { _, output ->
                ModItems.ITEMS.entries.map(DeferredHolder<Item, out Item>::get).forEach(output::accept)
            }.build()
    })
}