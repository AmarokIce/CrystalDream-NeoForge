package club.someoneice.crystaldream.api.handler

import club.someoneice.crystaldream.core.init.ModEffects
import club.someoneice.crystaldream.core.init.ModItems
import club.someoneice.crystaldream.util.instance
import com.google.common.collect.Sets
import net.minecraft.network.chat.Component
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Items
import kotlin.random.Random

object PlayerDreamHandler {
    private val DREAM_SET: LinkedHashSet<(Player) -> Unit> = Sets.newLinkedHashSet()
    fun randomDreamEvent(player: Player) = DREAM_SET.random()(player)

    init {
        DREAM_SET.add {
            it.addEffect(ModEffects.PUMPKIN_CORRODE.instance(Integer.MAX_VALUE,
                if (it.hasEffect(ModEffects.PUMPKIN_CORRODE)) it.getEffect(ModEffects.PUMPKIN_CORRODE)!!.amplifier + 1
                else 0)
            )

            it.sendSystemMessage(createMessage("pumpkin_dream"))
        }

        DREAM_SET.add {
            it.addEffect(MobEffects.MOVEMENT_SPEED.instance(20 * 60 * 10, 1))
            if (Random.nextDouble() <= 0.15) it.addItem(ModItems.DREAMING_ILLUSION.defaultInstance)
            it.sendSystemMessage(createMessage("paster_dream"))
        }

        DREAM_SET.add {
            it.addEffect(ModEffects.CRYSTAL_MIRROR.instance(20 * 60 * 10, 0))
            it.sendSystemMessage(createMessage("mirror_dream"))
        }

        DREAM_SET.add {
            it.giveExperiencePoints(Random.nextInt(50))
            it.sendSystemMessage(createMessage("exp-up_dream"))
        }

        DREAM_SET.add {
            it.addItem(Items.AMETHYST_SHARD.defaultInstance)
            if (Random.nextDouble() <= 0.02) it.addItem(Items.DIAMOND.defaultInstance)
            it.sendSystemMessage(createMessage("crystal_dream"))
        }

        DREAM_SET.add {
            it.sendSystemMessage(createMessage("none_dream"))
        }

        DREAM_SET.add {
            it.sendSystemMessage(createMessage("bda_dream"))
        }
    }

    private fun createMessage(name: String) = Component.translatable("dream.${name}.crystaldream.message")
}