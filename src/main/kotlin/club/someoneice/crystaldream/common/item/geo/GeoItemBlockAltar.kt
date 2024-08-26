package club.someoneice.crystaldream.common.item.geo

import club.someoneice.crystaldream.core.init.ModBlocks
import club.someoneice.crystaldream.util.createModPath
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import software.bernie.geckolib.animatable.GeoItem
import software.bernie.geckolib.animatable.client.GeoRenderProvider
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.*
import software.bernie.geckolib.model.GeoModel
import software.bernie.geckolib.renderer.GeoItemRenderer
import software.bernie.geckolib.util.GeckoLibUtil
import java.util.function.Consumer

class GeoItemBlockAltar : BlockItem(ModBlocks.MAGIC_ALTAR, Properties()), GeoItem {
    private val cache = GeckoLibUtil.createInstanceCache(this)

    override fun createGeoRenderer(consumer: Consumer<GeoRenderProvider>) {
        consumer.accept(object : GeoRenderProvider {
            private val renderer: BlockEntityWithoutLevelRenderer =
                object : GeoItemRenderer<GeoItemBlockAltar>(object : GeoModel<GeoItemBlockAltar>() {
                    override fun getModelResource(animatable: GeoItemBlockAltar): ResourceLocation =
                        createModPath("geo/magic_altar.geo.json")

                    override fun getTextureResource(animatable: GeoItemBlockAltar): ResourceLocation =
                        createModPath("textures/geo/magic_altar.png")

                    override fun getAnimationResource(animatable: GeoItemBlockAltar): ResourceLocation =
                        createModPath("animations/magic_altar.animation.json")
                }) {
                    override fun getRenderType(
                        animatable: GeoItemBlockAltar?,
                        texture: ResourceLocation?,
                        bufferSource: MultiBufferSource?,
                        partialTick: Float
                    ): RenderType? =
                        RenderType.entityTranslucent(getTextureLocation(animatable))
                }

            override fun getGeoItemRenderer(): BlockEntityWithoutLevelRenderer {
                return renderer
            }
        })
    }

    private val rawAnimation = RawAnimation.begin().thenLoop("magic_altar")
    private fun deployAnimController(state: AnimationState<GeoItemBlockAltar>): PlayState {
        return state.setAndContinue(rawAnimation)
    }

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController(this, this::deployAnimController))

        /*
        controllers.add(
            AnimationController(this, "magic_altar", 0) { PlayState.CONTINUE }
        )
        */
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return cache
    }
}