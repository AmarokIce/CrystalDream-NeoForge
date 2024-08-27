package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.common.menu.MenuNetherFurnace
import club.someoneice.crystaldream.core.CrystalDream
import club.someoneice.crystaldream.util.registerObject
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.inventory.MenuType
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object ModMenus {
    internal val MENUS: DeferredRegister<MenuType<*>> = DeferredRegister.create(BuiltInRegistries.MENU, CrystalDream.MODID)

    val NETHER_FURNACE: MenuType<MenuNetherFurnace> by MENUS.registerObject("nether_furnace",
        IMenuTypeExtension.create(::MenuNetherFurnace)
    )
}