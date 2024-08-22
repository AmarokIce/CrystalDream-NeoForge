package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.core.CrystalDream
import club.someoneice.crystaldream.core.menu.MenuNetherFurnace
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.inventory.MenuType
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import java.util.function.Supplier

object ModMenus {
    internal val MENUS: DeferredRegister<MenuType<*>> = DeferredRegister.create(BuiltInRegistries.MENU, CrystalDream.MODID)

    val NETHER_FURNACE: MenuType<MenuNetherFurnace> by MENUS.register("nether_furnace", Supplier<MenuType<MenuNetherFurnace>> {
        IMenuTypeExtension.create(::MenuNetherFurnace)
    })
}