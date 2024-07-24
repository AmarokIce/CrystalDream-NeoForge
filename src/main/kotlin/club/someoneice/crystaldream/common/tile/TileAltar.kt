package club.someoneice.crystaldream.common.tile

import club.someoneice.crystaldream.api.IItemTile
import club.someoneice.crystaldream.api.TileBase
import club.someoneice.crystaldream.core.init.ModTiles.ALTAR_CORE
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import software.bernie.geckolib.animatable.GeoBlockEntity
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.*
import software.bernie.geckolib.util.GeckoLibUtil

class TileAltar(pos: BlockPos, state: BlockState): TileBase(ALTAR_CORE, pos, state), IItemTile, GeoBlockEntity {
    override var itemHolder: ItemStack = ItemStack.EMPTY

    override fun writeToNbt(nbt: CompoundTag, registries: HolderLookup.Provider) {
        this.saveItem(nbt, registries)
    }

    override fun readFromNbt(nbt: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(nbt, registries)
    }

    private val cache = GeckoLibUtil.createInstanceCache(this)
    private val rawAnimation = RawAnimation.begin().thenLoop("magic_altar")

    private fun deployAnimController(state: AnimationState<TileAltar>): PlayState {
        return state.setAndContinue(rawAnimation)
    }

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController(this, this::deployAnimController))
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return this.cache
    }

    companion object {
        fun tick(world: Level, pos: BlockPos, state: BlockState, tile: TileAltar) {
            // TODO
        }
    }
}