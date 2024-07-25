package club.someoneice.crystaldream.common.item.geo

import club.someoneice.crystaldream.core.init.ModBlocks
import club.someoneice.crystaldream.util.createModPath
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
import software.bernie.geckolib.animatable.GeoItem
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.animation.AnimationController
import software.bernie.geckolib.animation.PlayState
import software.bernie.geckolib.model.GeoModel
import software.bernie.geckolib.renderer.GeoItemRenderer
import software.bernie.geckolib.util.GeckoLibUtil
import java.util.function.Consumer

class GeoItemBlockAltar : BlockItem(ModBlocks.MAGIC_ALTAR, Properties()), GeoItem {
    private val cache = GeckoLibUtil.createInstanceCache(this)

    override fun initializeClient(consumer: Consumer<IClientItemExtensions?>) {
        super.initializeClient(consumer)
        consumer.accept(object : IClientItemExtensions {
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

            override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer {
                return renderer
            }
        })
    }

    override fun registerControllers(data: AnimatableManager.ControllerRegistrar) {
        data.add(
            AnimationController(this, "magic_altar", 0) { PlayState.CONTINUE }
        )
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return cache
    }
}