package club.someoneice.crystaldream.api

import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.item.ItemStack

interface IItemTile {
    var itemHolder: ItemStack

    fun loadItem(nbt: CompoundTag, registries: HolderLookup.Provider) {
        itemHolder =
            if (nbt.contains("item")) ItemStack.parseOptional(registries, nbt.getCompound("item")) else ItemStack.EMPTY
        this.markItemChanged()
    }

    fun saveItem(nbt: CompoundTag, registries: HolderLookup.Provider) {
        if (!itemHolder.isEmpty) nbt.put("item", itemHolder.save(registries))
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
