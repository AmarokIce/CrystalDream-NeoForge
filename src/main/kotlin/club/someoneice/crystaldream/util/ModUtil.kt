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
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

fun createModPath(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(CrystalDream.MODID, path)
fun createModInfo(info: String, name: String) = "${info}.crystaldream.${name}"

fun getRandomSpeedRanges(random: RandomSource): Vec3 {
    return Vec3(
        Mth.nextDouble(random, -0.5, 0.5),
        Mth.nextDouble(random, -0.5, 0.5),
        Mth.nextDouble(random, -0.5, 0.5)
    )
}

fun createAABBByRange(pos: Vec3, range: Int): AABB {
    return AABB(pos.x - range, pos.y - range, pos.z - range, pos.x + range, pos.y + range, pos.z + range)
}

fun <T: BlockEntity> createTile(factory: BlockEntityType.BlockEntitySupplier<out T>, vararg blocks: Block): BlockEntityType<T> {
    return BlockEntityType.Builder.of(factory, *blocks).build(null)
}

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

fun <T, I: T> DeferredRegister<T>.registerObject(name: String, data: I): DeferredHolder<T, I> {
    return this.register(name, Supplier<I> { data })
}