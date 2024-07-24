package club.someoneice.crystaldream.client.geo.model

import club.someoneice.crystaldream.CrystalDream
import club.someoneice.crystaldream.common.tile.TileCrystalBall
import net.minecraft.resources.ResourceLocation
import software.bernie.geckolib.model.GeoModel

class GeoCrystalBall: GeoModel<TileCrystalBall>() {
    override fun getModelResource(animatable: TileCrystalBall): ResourceLocation = ResourceLocation(CrystalDream.MODID, "geo/crystal_ball.geo.json")
    override fun getTextureResource(animatable: TileCrystalBall): ResourceLocation = ResourceLocation(CrystalDream.MODID, "textures/geo/crystal_ball.png")
    override fun getAnimationResource(animatable: TileCrystalBall): ResourceLocation = ResourceLocation(CrystalDream.MODID, "animations/crystal_ball.animation.json")
}