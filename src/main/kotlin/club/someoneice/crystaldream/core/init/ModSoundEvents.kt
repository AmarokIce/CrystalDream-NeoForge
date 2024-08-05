package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.core.CrystalDream
import club.someoneice.crystaldream.util.createModPath
import com.google.common.base.Supplier
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.SoundEvent
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object ModSoundEvents {
    internal val SOUNDS: DeferredRegister<SoundEvent> =
        DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, CrystalDream.MODID)

    val GOBLINS_HAPPY by SOUNDS.register("goblins_happy", Supplier<SoundEvent> {
        SoundEvent.createVariableRangeEvent(createModPath("goblins_happy"))
    })
}
