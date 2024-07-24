package club.someoneice.crystaldream.client.geo.model

import club.someoneice.crystaldream.CrystalDream
import club.someoneice.crystaldream.common.tile.TileAltar
import net.minecraft.resources.ResourceLocation
import software.bernie.geckolib.model.GeoModel

class GeoAltar: GeoModel<TileAltar>() {
    override fun getModelResource(animatable: TileAltar): ResourceLocation = ResourceLocation(CrystalDream.MODID, "geo/magic_altar.geo.json")
    override fun getTextureResource(animatable: TileAltar): ResourceLocation = ResourceLocation(CrystalDream.MODID, "textures/geo/magic_altar.png")
    override fun getAnimationResource(animatable: TileAltar): ResourceLocation = ResourceLocation(CrystalDream.MODID, "animations/magic_altar.animation.json")
}