package club.someoneice.crystaldream.common.item.manacrystal

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class CrystalSaturation : AbstractManaCrystal() {
    override fun getMaxMana(): Int = 60
    fun usageMana(): Int = 10

    override fun onRecord(item: ItemStack, player: Player, world: Level): Boolean {
        return false
    }

    override fun onRelease(item: ItemStack, player: Player, world: Level): Boolean {
        player.foodData.foodLevel = 20
        return true
    }
}