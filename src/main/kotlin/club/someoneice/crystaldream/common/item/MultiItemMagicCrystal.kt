package club.someoneice.crystaldream.common.item

import club.someoneice.crystaldream.core.init.ModEffects
import club.someoneice.crystaldream.util.*
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LightningBolt
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag

sealed class MultiItemMagicCrystal(val name: String): Item(Properties().stacksTo(1)) {
    fun shirkMana() = 5

    abstract fun work(staff: ItemStack, crystal: ItemStack, world: ServerLevel, player: ServerPlayer)

    class SunCrystal: MultiItemMagicCrystal("suncrytal") {
        override fun work(staff: ItemStack, crystal: ItemStack, world: ServerLevel, player: ServerPlayer) {
            val data = player.foodData
            if (data.foodLevel < 6) {
                player.sendClientDisplayMessage(createModInfo("crystal", "no_hunger"))
            }
            data.foodLevel -= 3
            world.setWeatherParameters(ServerLevel.RAIN_DELAY.sample(world.random), 0, false, false)
        }
    }

    class RainCrystal: MultiItemMagicCrystal("raincrytal") {
        override fun work(staff: ItemStack, crystal: ItemStack, world: ServerLevel, player: ServerPlayer) {
            val data = player.foodData
            if (data.foodLevel < 6) {
                player.sendClientDisplayMessage(createModInfo("crystal", "no_hunger"))
            }
            data.foodLevel -= 3
            world.setWeatherParameters(0, ServerLevel.RAIN_DURATION.sample(world.random), true, false)
        }
    }

    class WindCrystal: MultiItemMagicCrystal("windcrystal") {
        override fun work(staff: ItemStack, crystal: ItemStack, world: ServerLevel, player: ServerPlayer) {
            val data = player.foodData
            if (data.foodLevel < 6) {
                player.sendClientDisplayMessage(createModInfo("crystal", "no_hunger"))
            }
            data.foodLevel -= 3
            world.setWeatherParameters(0, ServerLevel.THUNDER_DURATION.sample(world.random), true, true)
        }
    }

    class StorageCrystal: MultiItemMagicCrystal("storagecrystal") {
        override fun work(staff: ItemStack, crystal: ItemStack, world: ServerLevel, player: ServerPlayer) {
            TODO("Not yet implemented")
        }
    }

    class LightningCrystal: MultiItemMagicCrystal("lightningcrystal") {
        override fun work(staff: ItemStack, crystal: ItemStack, world: ServerLevel, player: ServerPlayer) {
            world.getEntitiesOfClass(LivingEntity::class.java, createAABBByRange(player.position(), 8)).forEach {
                if (it === player) {
                    return@forEach
                }

                world.addFreshEntity(LightningBolt(EntityType.LIGHTNING_BOLT, world).apply {
                    this.setPos(it.position())
                    this.damage = 15f
                })
            }
        }
    }

    class FoxFireCrystal: MultiItemMagicCrystal("foxfirecrystal") {
        override fun work(staff: ItemStack, crystal: ItemStack, world: ServerLevel, player: ServerPlayer) {
            world.getEntitiesOfClass(LivingEntity::class.java, createAABBByRange(player.position(), 8)).forEach {
                if (it === player) {
                    return@forEach
                }

                it.remainingFireTicks = 72000
                world.sendParticles(player, ParticleTypes.SOUL_FIRE_FLAME, false, it.x, it.y + 1, it.z, 8, 1.2, 0.8, 1.2, 0.55)
            }
        }
    }

    class HealCrystal: MultiItemMagicCrystal("healcrystal") {
        override fun work(staff: ItemStack, crystal: ItemStack, world: ServerLevel, player: ServerPlayer) {
            world.getEntitiesOfClass(Player::class.java, createAABBByRange(player.position(), 4)).forEach {
                it.activeEffects.removeIf(MobEffectInstance::isNeutral)
                it.removeEffect(ModEffects.CRYSTAL_MIRROR)
                it.addEffect(MobEffects.REGENERATION.instance(20 * 10, 1))
            }
        }
    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        tooltipComponents.add(Component.translatable(createModInfo(this.name, "info")))
    }

    override fun equals(other: Any?): Boolean = this === other
    override fun hashCode(): Int = super.hashCode()
}