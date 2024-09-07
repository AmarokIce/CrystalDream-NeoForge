package club.someoneice.crystaldream.common.effect

import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.neoforged.neoforge.common.EffectCure
import java.awt.Color

class EffectPumpkinCorrode: MobEffect(MobEffectCategory.NEUTRAL, Color.ORANGE.rgb) {
    override fun applyEffectTick(livingEntity: LivingEntity, amplifier: Int): Boolean = false
    override fun fillEffectCures(cures: MutableSet<EffectCure>, effectInstance: MobEffectInstance) {
        cures.clear()
    }
}