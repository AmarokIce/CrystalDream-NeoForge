package club.someoneice.crystaldream.common.tile

import club.someoneice.crystaldream.api.IItemHolder
import club.someoneice.crystaldream.core.init.ModTiles.TREE_TABLE
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.BlockState

class TileTree(pos: BlockPos, state: BlockState) : TileBase(TREE_TABLE, pos, state), IItemHolder {
    override var itemHolder: ItemStack = ItemStack.EMPTY

    override fun writeToNbt(nbt: CompoundTag, registries: HolderLookup.Provider) {
        this.saveItem(nbt, registries)
    }

    override fun readFromNbt(nbt: CompoundTag, registries: HolderLookup.Provider) {
        this.loadItem(nbt, registries)
    }

    companion object
}