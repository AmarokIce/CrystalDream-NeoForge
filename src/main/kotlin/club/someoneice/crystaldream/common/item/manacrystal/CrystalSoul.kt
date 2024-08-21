package club.someoneice.crystaldream.common.item.manacrystal

import club.someoneice.crystaldream.api.AbstractManaCrystal
import club.someoneice.crystaldream.util.updateNbt
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class CrystalSoul : AbstractManaCrystal() {
    override fun getMaxMana(): Int = 0

    override fun onRecord(item: ItemStack, player: Player, world: Level): Boolean {
        if (player.activeEffects.isEmpty()) {
            return false
        }

        val tagList = ListTag()
        player.activeEffects.forEach {
            tagList.add(it.save())
        }

        item.updateNbt {
            it.put("effects", tagList)
        }
        player.removeAllEffects()

        return true
    }

    override fun onRelease(item: ItemStack, player: Player, world: Level): Boolean {
        var tag: ListTag? = null
        item.updateNbt {
            tag = it.get("effects") as ListTag?
        }

        tag?.forEach {
            player.addEffect(MobEffectInstance.load(it as CompoundTag) ?: return@forEach)
        } ?: return false

        return true
    }
}