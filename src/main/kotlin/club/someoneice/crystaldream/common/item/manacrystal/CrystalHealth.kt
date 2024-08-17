package club.someoneice.crystaldream.common.item.manacrystal

import club.someoneice.crystaldream.api.AbstractManaCrystal
import club.someoneice.crystaldream.util.updateNbt
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class CrystalHealth: AbstractManaCrystal() {
    override fun getMaxMana(): Int = 0
    fun usageMana(): Int = 0

    override fun onRecord(item: ItemStack, player: Player, world: Level): Boolean {
        if (player.health <= 6) {
            return false
        }

        player.health -= 2
        item.updateNbt {
            it.putInt("health_data_hunger", it.getInt("health_data_hunger") + 2)
        }
        return true
    }

    override fun onRelease(item: ItemStack, player: Player, world: Level): Boolean {
        item.updateNbt {
            val health = it.getInt("health_data_hunger")
            val needHealthLevel = player.maxHealth - player.health
            if (health >= needHealthLevel) {
                it.putInt("health_data_hunger", (health - needHealthLevel).toInt())
                player.health = player.maxHealth
            } else {
                it.putInt("health_data_hunger", 0)
                player.health += health
            }
        }

        return true
    }
}