package club.someoneice.crystaldream.util

import club.someoneice.crystaldream.core.CrystalDream
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.core.BlockPos
import net.minecraft.core.Holder
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.util.RandomSource
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
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
fun MobEffectInstance.isNeutral() = this.effect.value().category == MobEffectCategory.NEUTRAL

fun Player.giveOrThrowOut(item: ItemStack) {
    val world = this.level()
    if (world.isClientSide()) return
    if (!this.addItem(item)) world.addFreshEntity(ItemEntity(world, this.x, this.y, this.z, item))
}

fun Player.sendClientDisplayMessage(str: String) {
    if (this.level().isClientSide()) {
        return
    }
    this.displayClientMessage(Component.translatable(str), true)
}

fun <T, I: T> DeferredRegister<T>.registerObject(name: String, data: I): DeferredHolder<T, I> {
    return this.register(name, Supplier<I> { data })
}

fun createBlockPosArrayWithRange(pos: Int, origin: BlockPos): Array<BlockPos> {
    return arrayOf(
        origin.offset(pos, 0, pos),
        origin.offset(pos, 0, 0),
        origin.offset(0, 0, pos),
        origin.offset(-pos, 0, -pos),
        origin.offset(-pos, 0, 0),
        origin.offset(0, 0, -pos),
        origin.offset(pos, 0, -pos),
        origin.offset(-pos, 0, pos)
    )
}

fun spawnParticlesAs(world: ClientLevel, posStart: BlockPos, posEnd: BlockPos, type: ParticleOptions, step: Int) {
    val start = posStart.center
    val end = posEnd.center

    val offsetX = (end.x - start.x) / step
    val offsetY = (end.y - start.y) / step
    val offsetZ = (end.z - start.z) / step

    var stepX = start.x
    var stepY = start.y
    var stepZ = start.z

    for (i in 0 until step) {
        stepX += offsetX
        stepY += offsetY
        stepZ += offsetZ

        world.addParticle(type, stepX, stepY, stepZ, 0.0, 0.0, 0.0)
    }
}