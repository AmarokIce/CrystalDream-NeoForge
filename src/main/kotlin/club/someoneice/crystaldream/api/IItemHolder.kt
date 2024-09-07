package club.someoneice.crystaldream.api

import club.someoneice.crystaldream.common.block.tile.TileBase
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack

interface IItemHolder {
    var itemHolder: ItemStack

    fun loadItem(nbt: CompoundTag, registries: HolderLookup.Provider) {
        itemHolder =
            if (nbt.getBoolean("hadItemHolder")) {
                ItemStack.parseOptional(registries, nbt.getCompound("itemHolder"))
            } else {
                nbt.remove("itemHolder")
                ItemStack.EMPTY
            }
        markItemChanged()
    }

    fun saveItem(nbt: CompoundTag, registries: HolderLookup.Provider) {
        if (itemHolder.isEmpty) {
            nbt.remove("itemHolder")
            nbt.putBoolean("hadItemHolder", false)
        } else {
            nbt.put("itemHolder", itemHolder.save(registries))
            nbt.putBoolean("hadItemHolder", true)
        }
    }

    fun markItemChanged() {
        if (this is TileBase) this.markDirt()
    }

    fun setItem(item: ItemStack) {
        this.itemHolder = item
        this.markItemChanged()
    }

    fun cleanItem() {
        setItem(ItemStack.EMPTY)
    }

    fun getItem(): ItemStack {
        return this.itemHolder.copy()
    }

    fun getAndCleanItem(): ItemStack {
        val item = this.getItem()
        cleanItem()
        return item
    }
}
