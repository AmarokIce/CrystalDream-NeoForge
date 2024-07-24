package club.someoneice.crystaldream.client.gui.widget

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractButton
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import java.awt.Color

abstract class CrystalButton(private val texture: ResourceLocation,
                    private val pX: Int,
                    private val pY: Int,
                    private val u: Int,
                    private val v: Int,
                    private val w: Int,
                    private val h: Int,
                    private val offsetX: Int,
                    private val offsetY: Int,
                    private val tooltip: Component
): AbstractButton(pX, pY, w, h, tooltip) {
    override fun renderWidget(pGuiGraphics: GuiGraphics, pMouseX: Int, pMouseY: Int, pPartialTick: Float) {
        if (mouseIn(pMouseX, pMouseY)) pGuiGraphics.blit(texture, pX, pY, u + offsetX, v + offsetY, w, h)
        else pGuiGraphics.blit(texture, pX, pY, u, v, w, h)

        val width = font.width(tooltip)
        val renderX = pX + (w / 2 - width / 2)
        val renderY = pY + (h / 2 - font.lineHeight / 2)

        pGuiGraphics.drawString(font, tooltip, renderX, renderY, Color.WHITE.rgb)
    }

    override fun updateWidgetNarration(pNarrationElementOutput: NarrationElementOutput) {
    }

    fun mouseIn(mouseX: Int, mouseY: Int): Boolean = mouseX > pX && mouseY > pY && mouseX < pX + w && mouseY < pY + h

    companion object {
        private val mc = Minecraft.getInstance();
        private val font = mc.font
    }
}