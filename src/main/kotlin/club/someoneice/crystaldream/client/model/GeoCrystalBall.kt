package club.someoneice.crystaldream.client.model

import club.someoneice.crystaldream.common.block.tile.TileCrystalBall
import club.someoneice.crystaldream.util.createModPath
import net.minecraft.resources.ResourceLocation
import software.bernie.geckolib.model.GeoModel

class GeoCrystalBall : GeoModel<TileCrystalBall>() {
    override fun getModelResource(animatable: TileCrystalBall): ResourceLocation =
        createModPath("geo/crystal_ball.geo.json")

    override fun getTextureResource(animatable: TileCrystalBall): ResourceLocation =
        createModPath("textures/geo/crystal_ball.png")

    override fun getAnimationResource(animatable: TileCrystalBall): ResourceLocation =
        createModPath("animations/crystal_ball.animation.json")
}