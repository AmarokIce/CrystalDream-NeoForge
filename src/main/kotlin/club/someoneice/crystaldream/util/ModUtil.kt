package club.someoneice.crystaldream.util

import club.someoneice.crystaldream.core.CrystalDream
import net.minecraft.core.Holder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack

fun createModPath(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(CrystalDream.MODID, path)

fun Holder<MobEffect>.instance(time: Int = 20 * 3, amplifier: Int = 0) = MobEffectInstance(this, time, amplifier)
fun MobEffectInstance.copy() = MobEffectInstance(this)

fun Player.giveOrThrowOut(item: ItemStack) {
    val world = this.level()
    if (world.isClientSide()) return
    if (!this.addItem(item)) world.addFreshEntity(ItemEntity(world, this.x, this.y, this.z, item))
}