package club.someoneice.crystaldream.client.model

import club.someoneice.crystaldream.common.tile.TileAltar
import club.someoneice.crystaldream.util.createModPath
import net.minecraft.resources.ResourceLocation
import software.bernie.geckolib.model.GeoModel

class GeoAltar : GeoModel<TileAltar>() {
    override fun getModelResource(animatable: TileAltar): ResourceLocation = createModPath("geo/magic_altar.geo.json")
    override fun getTextureResource(animatable: TileAltar): ResourceLocation =
        createModPath("textures/geo/magic_altar.png")

    override fun getAnimationResource(animatable: TileAltar): ResourceLocation =
        createModPath("animations/magic_altar.animation.json")
}