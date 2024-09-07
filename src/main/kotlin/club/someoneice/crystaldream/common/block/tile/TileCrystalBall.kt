package club.someoneice.crystaldream.common.block.tile

import club.someoneice.crystaldream.core.init.ModTiles.CRYSTAL_BALL
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import software.bernie.geckolib.animatable.GeoBlockEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.animation.AnimationController
import software.bernie.geckolib.animation.RawAnimation
import software.bernie.geckolib.util.GeckoLibUtil
import software.bernie.geckolib.util.RenderUtil

class TileCrystalBall(pos: BlockPos, state: BlockState) : BlockEntity(CRYSTAL_BALL, pos, state), GeoBlockEntity {
    private val cache = GeckoLibUtil.createInstanceCache(this)

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController(this) {
            it.setAndContinue(RawAnimation.begin().thenLoop("0"))
        })
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = this.cache
    override fun getTick(blockEntity: Any): Double = RenderUtil.getCurrentTick()
}