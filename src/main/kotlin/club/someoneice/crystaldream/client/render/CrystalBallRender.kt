package club.someoneice.crystaldream.client.render

import club.someoneice.crystaldream.client.model.GeoCrystalBall
import club.someoneice.crystaldream.common.block.tile.TileCrystalBall
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import software.bernie.geckolib.renderer.GeoBlockRenderer

class CrystalBallRender : GeoBlockRenderer<TileCrystalBall>(GeoCrystalBall()),
    BlockEntityRendererProvider<TileCrystalBall> {
    override fun create(pContext: BlockEntityRendererProvider.Context): BlockEntityRenderer<TileCrystalBall> = this
}