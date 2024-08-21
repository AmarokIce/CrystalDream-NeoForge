package club.someoneice.crystaldream.util

import net.minecraft.core.component.DataComponents
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.CustomData
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.Level

fun ItemLike.asIngredient() = Ingredient.of(this)
fun ItemLike.asStack(count: Int = 1) = ItemStack(this, count)
fun ItemLike.asEntityAndSpawn(world: Level, posX: Double, posY: Double, posZ: Double, size: Int = 1) {
    this.asStack(size).asEntityAndSpawn(world, posX, posY, posZ)
}

fun ItemStack.asEntityAndSpawn(world: Level, posX: Double, posY: Double, posZ: Double) {
    if (this.isEmpty) return
    val item = ItemEntity(world, posX, posY, posZ, this)
    world.addFreshEntity(item)
}

fun ItemStack.cost(size: Int = 1): ItemStack {
    this.shrink(size)
    return this
}

fun ItemStack.updateNbt(data: (CompoundTag) -> Unit) {
    val nbt = this.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)
    nbt.update { data(it) }
    this.set(DataComponents.CUSTOM_DATA, nbt)
}