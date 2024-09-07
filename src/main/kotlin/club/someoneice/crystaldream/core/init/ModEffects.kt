package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.common.effect.EffectCrystalMirror
import club.someoneice.crystaldream.common.effect.EffectPumpkinCorrode
import club.someoneice.crystaldream.core.CrystalDream
import club.someoneice.crystaldream.util.registerObject
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.effect.MobEffect
import net.neoforged.neoforge.registries.DeferredRegister

object ModEffects {
    internal val EFFECTS: DeferredRegister<MobEffect> = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, CrystalDream.MODID)

    val CRYSTAL_MIRROR = EFFECTS.registerObject("crystal_mirror", EffectCrystalMirror())
    val PUMPKIN_CORRODE = EFFECTS.registerObject("pumpkin_corrode", EffectPumpkinCorrode())
}