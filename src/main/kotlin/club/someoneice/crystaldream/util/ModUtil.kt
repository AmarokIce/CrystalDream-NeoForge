package club.someoneice.crystaldream.util

import club.someoneice.crystaldream.core.CrystalDream
import net.minecraft.core.Holder
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.phys.Vec3

fun createModPath(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(CrystalDream.MODID, path)
fun createModInfo(info: String, name: String) = "${info}.crystaldream.${name}"


fun Holder<MobEffect>.instance(time: Int = 20 * 3, amplifier: Int = 0) = MobEffectInstance(this, time, amplifier)
fun MobEffectInstance.copy() = MobEffectInstance(this)

fun Player.giveOrThrowOut(item: ItemStack) {
    val world = this.level()
    if (world.isClientSide()) return
    if (!this.addItem(item)) world.addFreshEntity(ItemEntity(world, this.x, this.y, this.z, item))
}

fun Player.sendClientDisplayMessage(str: String) {
    if (!this.level().isClientSide()) return
    this.displayClientMessage(Component.translatable(str), true)
}

fun getRandomSpeedRanges(random: RandomSource): Vec3 {
    return Vec3(
        Mth.nextDouble(random, -0.5, 0.5),
        Mth.nextDouble(random, -0.5, 0.5),
        Mth.nextDouble(random, -0.5, 0.5)
    )
}