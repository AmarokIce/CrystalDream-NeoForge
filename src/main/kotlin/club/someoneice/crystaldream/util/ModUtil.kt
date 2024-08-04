package club.someoneice.crystaldream.util

import club.someoneice.crystaldream.core.CrystalDream
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.Level

fun createModPath(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(CrystalDream.MODID, path)

fun Holder<MobEffect>.instance(time: Int = 20 * 3, amplifier: Int = 0) = MobEffectInstance(this, time, amplifier)
fun MobEffectInstance.copy() = MobEffectInstance(this)

fun ItemLike.asIngredient() = Ingredient.of(this)
fun ItemLike.asStack(count: Int = 1) = ItemStack(this, count)

fun Player.giveOrThrowOut(item: ItemStack) {
    val world = this.level()
    if (world.isClientSide()) return
    if (!this.addItem(item)) world.addFreshEntity(ItemEntity(world, this.x, this.y, this.z, item))
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


