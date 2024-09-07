package club.someoneice.crystaldream.common.effect

import club.someoneice.crystaldream.core.init.ModDamages
import com.google.common.collect.Sets
import net.minecraft.resources.ResourceKey
import net.minecraft.world.damagesource.DamageType
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.neoforged.neoforge.common.EffectCure
import java.awt.Color

class EffectCrystalMirror: MobEffect(MobEffectCategory.NEUTRAL, Color(166, 0, 166).rgb) {
    companion object {
        val IGNORE: HashSet<ResourceKey<DamageType>> = Sets.newHashSet(
            ModDamages.CRYSTAL_REFLECTION,
            ModDamages.CORRODE
        )
    }

    override fun applyEffectTick(livingEntity: LivingEntity, amplifier: Int): Boolean = false
    override fun fillEffectCures(cures: MutableSet<EffectCure>, effectInstance: MobEffectInstance) {
        cures.clear()
    }
}