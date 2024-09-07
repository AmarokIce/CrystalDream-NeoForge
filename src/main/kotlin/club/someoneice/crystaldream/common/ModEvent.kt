package club.someoneice.crystaldream.common

import club.someoneice.crystaldream.common.effect.EffectCrystalMirror
import club.someoneice.crystaldream.core.CrystalDream
import club.someoneice.crystaldream.core.init.ModDamages
import club.someoneice.crystaldream.core.init.ModEffects
import club.someoneice.crystaldream.core.init.ModItems
import club.someoneice.crystaldream.util.PlayerSleepHandler
import club.someoneice.crystaldream.util.copy
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.LivingHealEvent
import net.neoforged.neoforge.event.entity.player.PlayerEvent
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent
import kotlin.math.min

@EventBusSubscriber(modid = CrystalDream.MODID)
object ModEvent {
    @SubscribeEvent
    fun burnTimeEvent(event: FurnaceFuelBurnTimeEvent) {
        event.burnTime = when (event.itemStack.item) {
            ModItems.WOODEN_BOTTLE -> 100
            ModItems.OAK_ESS -> 1200
            ModItems.SOUL -> 1200
            ModItems.EMPTY_MANA_PAGE -> 100
            ModItems.SHEPHERD_STAFF -> 1800
            else -> return
        }
    }

    @SubscribeEvent
    fun entityHurtEvent(event: LivingDamageEvent.Pre) {
        val entity = event.entity
        val damage = event.source
        val damageValue = event.originalDamage

        val flag = entity.hasEffect(ModEffects.PUMPKIN_CORRODE)
        val lv = if (!flag) 0 else entity.getEffect(ModEffects.PUMPKIN_CORRODE)!!.amplifier

        if (EffectCrystalMirror.IGNORE.any(damage::`is`)) {
            return
        }

        event.newDamage = 0f

        if (entity.hasEffect(ModEffects.CRYSTAL_MIRROR.delegate)) {
            entity.health = min(entity.maxHealth, entity.health + damageValue / (if (!flag) 1.0f else 2.0f + lv))
            return
        }

        if (flag) {
            entity.hurt(ModDamages.damageCorrode(entity), damageValue * (2 + lv))
            return
        }
    }

    @SubscribeEvent
    fun entityHealEvent(event: LivingHealEvent) {
        val entity = event.entity
        val value = event.amount

        val flag = entity.hasEffect(ModEffects.CRYSTAL_MIRROR)
        val lv = if (!flag) 0 else entity.getEffect(ModEffects.CRYSTAL_MIRROR)!!.amplifier

        if (entity.hasEffect(ModEffects.CRYSTAL_MIRROR.delegate)) {
            entity.hurt(ModDamages.damageCrystalReflection(entity), value * (if (!flag) 1.0f else 2.0f + lv))
            event.isCanceled = true
            return
        }

        if (flag) {
            event.amount = value / (2 + lv)
            return
        }
    }

    @SubscribeEvent
    fun playerWeekupEvent(event: PlayerWakeUpEvent) {
        if (event.updateLevel()) {
            return
        }

        PlayerSleepHandler.doDreamEvent(event.entity)
    }

    @SubscribeEvent
    fun playerRespawnEvent(event: PlayerEvent.Clone) {
        val old = event.original
        val new = event.entity

        if (old.hasEffect(ModEffects.CRYSTAL_MIRROR)) {
            new.addEffect(old.getEffect(ModEffects.CRYSTAL_MIRROR)!!.copy())
        }

        if (old.hasEffect(ModEffects.PUMPKIN_CORRODE)) {
            new.addEffect(old.getEffect(ModEffects.PUMPKIN_CORRODE)!!.copy())
        }
    }
}