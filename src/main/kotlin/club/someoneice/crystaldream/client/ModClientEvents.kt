package club.someoneice.crystaldream.client

import club.someoneice.crystaldream.api.AbstractHoldItemTileRender
import club.someoneice.crystaldream.client.render.CrystalBallRender
import club.someoneice.crystaldream.client.render.MagicAltarRender
import club.someoneice.crystaldream.client.screen.ScreenNetherFurnace
import club.someoneice.crystaldream.common.tile.TileMount
import club.someoneice.crystaldream.common.tile.TileTree
import club.someoneice.crystaldream.core.CrystalDream
import club.someoneice.crystaldream.core.init.ModBlocks
import club.someoneice.crystaldream.core.init.ModMenus
import club.someoneice.crystaldream.core.init.ModTiles
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.level.block.entity.BlockEntity
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent

@Suppress("Deprecated")
@EventBusSubscriber(modid = CrystalDream.MODID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object ModClientEvents {
    @SubscribeEvent
    fun registerParticles(event: RegisterParticleProvidersEvent) {
        // Minecraft.getInstance().particleEngine.register(ParticleInit.ORE_BREAK, ParticleOreBreak::provider)
        // Minecraft.getInstance().particleEngine.register(ParticleInit.ORE_SHINY, ParticleOreShiny::provider)
    }

    @SubscribeEvent
    fun registerRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerBlockEntityRenderer(ModTiles.TREE_TABLE, createRendererWithItemHolder<TileTree>())
        event.registerBlockEntityRenderer(ModTiles.ALTAR_MOUNT, createRendererWithItemHolder<TileMount>())
        event.registerBlockEntityRenderer(ModTiles.ALTAR_CORE, MagicAltarRender())
        event.registerBlockEntityRenderer(ModTiles.CRYSTAL_BALL, CrystalBallRender())
    }

    @SubscribeEvent
    fun clientCommonInit(event: FMLClientSetupEvent) {
        // MenuScreens.register(MenuInit.CRYSTAL_MAKER, ::GuiCrystalMaker)

        event.enqueueWork {
            layout()
        }
    }

    private fun layout() {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.TREE_TABLE, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MAGIC_MOUNT, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MAGIC_ALTAR, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CRYSTAL_BALL, RenderType.cutout())
    }

    @SubscribeEvent
    fun registerScreens(event: RegisterMenuScreensEvent) {
        event.register(ModMenus.NETHER_FURNACE, ::ScreenNetherFurnace)
    }

    private fun <T : BlockEntity> createRendererWithItemHolder() = object : AbstractHoldItemTileRender<T>() {
        override fun render(entity: T, f: Float, pose: PoseStack, buffer: MultiBufferSource, i: Int, k: Int) {
            super.absRender(1.0, entity, f, pose, buffer, i)
        }
    }

    @JvmName("# replaceMissingNo")
    @Deprecated("Never used")
    private fun replaceMissingNo() {
        // The reflection will not work in Java 9+ :(
        // I'll got take some mixins.
        /*
        val field = MissingTextureAtlasSprite::class.java.getDeclaredField("MISSING_TEXTURE_LOCATION")
        field.isAccessible = true

        val getDeclaredFields0 = Class::class.java.getDeclaredMethod("getDeclaredFields0", Boolean::class.java)
        getDeclaredFields0.setAccessible(true)
        val fields = getDeclaredFields0.invoke(Field::class.java, false) as Array<Field>
        var modifierField: Field? = null
        for (f in fields) {
            if ("modifiers" == f.name) {
                modifierField = f
                break
            }
        }
        modifierField!!.setAccessible(true);
        modifierField.setInt(field, field.modifiers and Modifier.FINAL.inv())
        field.set(null, createModPath("missingno"));
        */
    }
}