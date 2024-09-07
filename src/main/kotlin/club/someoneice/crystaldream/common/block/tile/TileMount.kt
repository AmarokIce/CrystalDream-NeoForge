package club.someoneice.crystaldream.common.block.tile

import club.someoneice.crystaldream.api.IItemHolder
import club.someoneice.crystaldream.core.init.ModTiles.ALTAR_MOUNT
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState

class TileMount(pos: BlockPos, state: BlockState) : TileBase(ALTAR_MOUNT, pos, state), IItemHolder {
    override var itemHolder: ItemStack = ItemStack.EMPTY

    override fun writeToNbt(nbt: CompoundTag, register: HolderLookup.Provider) {
        this.saveItem(nbt, register)
    }

    override fun readFromNbt(nbt: CompoundTag, register: HolderLookup.Provider) {
        this.loadItem(nbt, register)
    }
}