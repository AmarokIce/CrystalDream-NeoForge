package club.someoneice.crystaldream.client.screen

import club.someoneice.crystaldream.core.menu.MenuNetherFurnace
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

    override fun renderBg(gg: GuiGraphics, delta: Float, mouseX: Int, mouseY: Int) {
        gg.setColor(1.0f, 1.0f, 1.0f, 1.0f)
        val x = (this.width - this.imageWidth) / 2
        val y = (this.height - this.imageHeight) / 2

        gg.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight)

        val burnTime = this.menu.getBurn()
        val progress = this.menu.getProgress()
        if (burnTime > 0) {
            gg.blit(TEXTURE, x + 62, y + 40 + (14 - burnTime), 193, 1 + (14 - burnTime), 14, burnTime)
        }
        if (progress > 0) {
            gg.blit(TEXTURE, x + 85, y + 36, 192, 17, progress, 14)
        }
    }

    override fun render(gg: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(gg, mouseX, mouseY, delta)
        super.render(gg, mouseX, mouseY, delta)
        this.renderTooltip(gg, mouseX, mouseY)
    }
}