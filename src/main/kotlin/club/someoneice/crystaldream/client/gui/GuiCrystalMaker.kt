package club.someoneice.crystaldream.client.gui

import club.someoneice.crystaldream.CrystalDream
import club.someoneice.crystaldream.client.gui.widget.CrystalButton
import club.someoneice.crystaldream.common.item.curios.BaseCurios
import club.someoneice.crystaldream.init.CapabilityInit
import club.someoneice.crystaldream.network.C2SPacketCrystalCreate
import club.someoneice.crystaldream.network.PostChannel
import club.someoneice.crystaldream.util.MessageData
import club.someoneice.crystaldream.util.MessageData.CRYSTAL_MAKER_CREATE
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.AbstractButton
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.player.Inventory
import java.awt.Color

class GuiCrystalMaker(val pMenu: ContainerCrystalMaker, private val pPlayerInventory: Inventory, pTitle: Component): AbstractContainerScreen<ContainerCrystalMaker>(pMenu, pPlayerInventory, pTitle) {
    private var x: Int = 0;
    private var y: Int = 0;
    private lateinit var button: AbstractButton

    init {
        this.imageWidth = 176
        this.imageHeight = 176
    }

    override fun init() {
        super.init()

        this.x = (this.width - this.imageWidth) / 2
        this.y = (this.height - this.imageHeight) / 2

        this.button = object: CrystalButton(TEXTURE, x + 56, y + 51, 192, 0, 32, 16, 0, 16, CRYSTAL_MAKER_CREATE) {
            override fun onPress() {
                PostChannel.CHANNEL.sendToServer(C2SPacketCrystalCreate(pMenu.pos))
            }
        }

        addRenderableWidget(button)
    }

    override fun renderBg(pGuiGraphics: GuiGraphics, pPartialTick: Float, pMouseX: Int, pMouseY: Int) {
        pGuiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f)

        val pose = pGuiGraphics.pose()

        x = (this.width - this.imageWidth) / 2
        y = (this.height - this.imageHeight) / 2
        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight)

        val w = FONT.width(MessageData.CRYSTAL_MAKER_LV)
        pPlayerInventory.player.getCapability(CapabilityInit.CRYSTAL_MAKER_CAPABILITY).ifPresent {
            pose.pushPose()
            run {
                val pX = (x * 2 + 55 * 2).toInt()
                val pY = (y * 2 + 35 * 2).toInt()
                pose.scale(0.5f, 0.5f, 0.5f)
                pGuiGraphics.drawString(FONT, MessageData.CRYSTAL_MAKER_LV, pX, pY, Color(146, 81, 171).rgb)
                pGuiGraphics.drawString(FONT, it.computeLv().toString(), pX + (w * 2).toInt(), pY, Color.YELLOW.rgb)
            }
            pose.popPose()
        }

        val recipe = pMenu.tile.findRecipe() ?: return
        val output = recipe.getOutputPreview()

        pGuiGraphics.renderItem(output, x + 123 + 33 / 2 - 8, y + 15)
        val width = font.width(output.displayName)
        pGuiGraphics.drawString(font, output.displayName.string, x + 123 + 33 / 2 - width / 2, y + 35, Color.WHITE.rgb)
        if (recipe.output !is BaseCurios) return
        val type = recipe.output.type

        pose.pushPose()
        run {
            pose.scale(0.5f, 0.5f, 0.5f)
            pGuiGraphics.drawString(font, type.component, x + 123 + 33 / 2 - width / 2, y + 45, type.color.color ?: Color.WHITE.rgb)
        }
        pose.popPose()
    }

    override fun render(gg: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(gg)
        super.render(gg, mouseX, mouseY, delta)
        this.renderTooltip(gg, mouseX, mouseY)
    }

    companion object {
        private val FONT = Minecraft.getInstance().font

        private val TEXTURE = ResourceLocation(CrystalDream.MODID, "textures/gui/crystal_maker.png")
    }
}
