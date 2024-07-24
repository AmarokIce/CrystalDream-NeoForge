package club.someoneice.crystaldream.common.item.geo

import club.someoneice.crystaldream.CrystalDream
import club.someoneice.crystaldream.init.BlockInit
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraftforge.client.extensions.common.IClientItemExtensions
import software.bernie.geckolib.animatable.GeoItem
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar
import software.bernie.geckolib.core.animation.AnimationController
import software.bernie.geckolib.core.`object`.PlayState
import software.bernie.geckolib.model.GeoModel
import software.bernie.geckolib.renderer.GeoItemRenderer
import software.bernie.geckolib.util.GeckoLibUtil
import java.util.function.Consumer

class GeoItemBlockAltar: BlockItem(BlockInit.MAGIC_ALTAR, Properties()), GeoItem {
    private val cache = GeckoLibUtil.createInstanceCache(this)

    override fun initializeClient(consumer: Consumer<IClientItemExtensions?>) {
        super.initializeClient(consumer)
        consumer.accept(object : IClientItemExtensions {
            private val renderer: BlockEntityWithoutLevelRenderer = object: GeoItemRenderer<GeoItemBlockAltar>(object: GeoModel<GeoItemBlockAltar>() {
                override fun getModelResource(animatable: GeoItemBlockAltar): ResourceLocation = ResourceLocation(CrystalDream.MODID, "geo/magic_altar.geo.json")
                override fun getTextureResource(animatable: GeoItemBlockAltar): ResourceLocation = ResourceLocation(CrystalDream.MODID, "textures/geo/magic_altar.png")
                override fun getAnimationResource(animatable: GeoItemBlockAltar): ResourceLocation = ResourceLocation(CrystalDream.MODID, "animations/magic_altar.animation.json")
            }) {
                override fun getRenderType(animatable: GeoItemBlockAltar?,
                                           texture: ResourceLocation?,
                                           bufferSource: MultiBufferSource?,
                                           partialTick: Float): RenderType? =
                    RenderType.entityTranslucent(getTextureLocation(animatable))
            }
            override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer {
                return renderer
            }
        })
    }

    override fun registerControllers(data: ControllerRegistrar) {
        data.add(
            AnimationController(this, "magic_altar", 0) { PlayState.CONTINUE }
        )
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return cache
    }
}