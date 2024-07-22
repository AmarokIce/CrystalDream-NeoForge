package club.someoneice.crystaldream.core

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

fun MobEffectInstance.copy() = MobEffectInstance(this)

fun Player.giveOrThrowOut(item: ItemStack) {
    val world = this.level()
    if (world.isClientSide()) return
    if (!this.addItem(item)) world.addFreshEntity(ItemEntity(world, this.x, this.y, this.z, item))
}