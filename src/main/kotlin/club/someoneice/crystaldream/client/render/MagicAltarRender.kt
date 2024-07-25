package club.someoneice.crystaldream.client.render

import club.someoneice.crystaldream.api.AbstractHoldItemTileRender
import club.someoneice.crystaldream.client.model.GeoAltar
import club.someoneice.crystaldream.common.tile.TileAltar
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import software.bernie.geckolib.renderer.GeoBlockRenderer
import software.bernie.geckolib.util.RenderUtil

class MagicAltarRender : GeoBlockRenderer<TileAltar>(GeoAltar()), BlockEntityRendererProvider<TileAltar> {
    override fun create(pContext: BlockEntityRendererProvider.Context): BlockEntityRenderer<TileAltar> = this
    private var item: ItemEntity? = null

    // override from Geo render.
    override fun render(tile: TileAltar, f: Float, pose: PoseStack, buffer: MultiBufferSource, i: Int, k: Int) {
        super.render(tile, f, pose, buffer, i, k)

        tile

        if (item == null) item = ItemEntity(
            tile.level!!,
            tile.blockPos.x.toDouble(),
            tile.blockPos.y.toDouble(),
            tile.blockPos.z.toDouble(),
            tile.getItem()
        )
        pose.pushPose()
        run {
            pose.translate(0.5, 1.675, 0.5)
            pose.scale(0.85f, 0.85f, 0.85f)
            item?.item = tile.getItem()
            if (item != null)
                Minecraft.getInstance().entityRenderDispatcher.getRenderer<Entity>(item!!)
                    .render(item, f, RenderUtil.getCurrentTick().toFloat(), pose, buffer, i)

            pose.pushPose()
            run {
                pose.translate(-0.350, -0.55, -0.74)
                pose.mulPose(AbstractHoldItemTileRender.rotation(1.0f, 0.0f, 0.0f, 22.4f))
                pose.mulPose(AbstractHoldItemTileRender.rotation(0.0f, 0.0f, 1.0f, 22f))
                pose.scale(0.4f, 0.4f, 0.1f)
            }
            pose.popPose()
        }
        pose.popPose()
    }
}