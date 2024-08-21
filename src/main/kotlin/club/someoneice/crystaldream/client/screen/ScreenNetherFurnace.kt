package club.someoneice.crystaldream.client.screen

import club.someoneice.crystaldream.client.menu.MenuNetherFurnace
import club.someoneice.crystaldream.util.createModPath
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory

class ScreenNetherFurnace(menu: MenuNetherFurnace, playerInventory: Inventory, title: Component)
    : AbstractContainerScreen<MenuNetherFurnace>(menu, playerInventory, title)
{
    companion object {
        val TEXTURE: ResourceLocation = createModPath("textures/gui/nether_furnace_gui.png")
    }

    override fun renderBg(gg: GuiGraphics, step: Float, mouseX: Int, mouseY: Int) {
        TODO("Not yet implemented")
    }
}