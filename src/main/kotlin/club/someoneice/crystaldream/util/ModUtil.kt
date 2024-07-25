package club.someoneice.crystaldream.util

import club.someoneice.crystaldream.core.CrystalDream
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

fun createModPath(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(CrystalDream.MODID, path)

fun MobEffectInstance.copy() = MobEffectInstance(this)

fun Player.giveOrThrowOut(item: ItemStack) {
    val world = this.level()
    if (world.isClientSide()) return
    if (!this.addItem(item)) world.addFreshEntity(ItemEntity(world, this.x, this.y, this.z, item))
}

fun ItemStack.asEntityAndSpawn(world: Level, posX: Double, posY: Double, posZ: Double) {
    val item = ItemEntity(world, posX, posY, posZ, this)
    world.addFreshEntity(item)
}