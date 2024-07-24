package club.someoneice.crystaldream.api

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.level.block.entity.BlockEntity
import org.joml.Quaternionf
import software.bernie.geckolib.util.RenderUtil
import kotlin.math.cos
import kotlin.math.sin

abstract class AbstractTileRender<T : BlockEntity> : BlockEntityRenderer<T>, BlockEntityRendererProvider<T> {
    override fun create(pContext: BlockEntityRendererProvider.Context): BlockEntityRenderer<T> = this
    var item: ItemEntity? = null

    fun absRender(y: Double, tile: T, f: Float, pose: PoseStack, buffer: MultiBufferSource, i: Int) {
        if (tile !is IItemTile) throw RuntimeException("Do not call a tile without IItemTileHelper use AbstractTileRender!")
        if (item == null) item = ItemEntity(
            tile.level!!,
            tile.blockPos.x.toDouble(),
            tile.blockPos.y.toDouble(),
            tile.blockPos.z.toDouble(),
            tile.getItem()
        )
        pose.pushPose()
        run {
            pose.translate(0.5, y, 0.5)
            pose.scale(0.855f, 0.855f, 0.855f)
            item?.item = tile.getItem()
            if (item != null)
                Minecraft.getInstance().entityRenderDispatcher.getRenderer<Entity>(item!!)
                    .render(item, f, RenderUtil.getCurrentTick().toFloat(), pose, buffer, i)

            pose.pushPose()
            run {
                pose.translate(-0.350, -0.55, -0.74)
                pose.mulPose(rotation(1.0f, 0.0f, 0.0f, 22.4f))
                pose.mulPose(rotation(0.0f, 0.0f, 1.0f, 22f))
                pose.scale(0.4f, 0.4f, 0.1f)
            }
            pose.popPose()
        }

        pose.popPose()
    }

    companion object {
        fun rotation(x: Float, y: Float, z: Float, rot: Float): Quaternionf {
            val rt = rot * (Math.PI.toFloat() / 180f)
            val w = cos(rt / 2.0f)
            val f: Float = sin(rt / 2.0f)
            return Quaternionf(x * f, y * f, z * f, w)
        }
    }
}