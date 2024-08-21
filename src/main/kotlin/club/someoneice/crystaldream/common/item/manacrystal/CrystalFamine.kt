package club.someoneice.crystaldream.common.item.manacrystal

import club.someoneice.crystaldream.api.AbstractManaCrystal
import club.someoneice.crystaldream.util.updateNbt
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class CrystalFamine : AbstractManaCrystal() {
    override fun getMaxMana(): Int = 0
    fun usageMana(): Int = 0

    override fun onRecord(item: ItemStack, player: Player, world: Level): Boolean {
        val foodData = player.foodData
        if (foodData.foodLevel <= 6) {
            return false
        }

        foodData.foodLevel -= 2
        item.updateNbt {
            it.putInt("food_data_hunger", it.getInt("food_data_hunger") + 2)
        }
        return true
    }

    override fun onRelease(item: ItemStack, player: Player, world: Level): Boolean {
        val foodData = player.foodData
        if (!foodData.needsFood()) {
            return false
        }

        item.updateNbt {
            val hunger = it.getInt("food_data_hunger")
            val needFoodLevel = 20 - foodData.foodLevel
            if (hunger >= needFoodLevel) {
                it.putInt("food_data_hunger", hunger - needFoodLevel)
                foodData.foodLevel = 20
            } else {
                it.putInt("food_data_hunger", 0)
                foodData.foodLevel += hunger
            }
        }

        return true
    }
}