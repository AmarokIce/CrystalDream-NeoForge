package club.someoneice.crystaldream.common.item.manacrystal

import club.someoneice.crystaldream.util.updateNbt
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class CrystalAddress : AbstractManaCrystal() {
    override fun getMaxMana(): Int = 60
    fun usageMana(): Int = 5

    override fun onRecord(item: ItemStack, player: Player, world: Level): Boolean {
        item.updateNbt {
            it.putLong("address_pos", player.onPos.asLong())
        }
        return true
    }

    override fun onRelease(item: ItemStack, player: Player, world: Level): Boolean {
        var pos: BlockPos? = null
        item.updateNbt {
            pos = BlockPos.of(it.getLong("address_pos"))
        }
        pos ?: return false
        player.teleportTo(pos!!.x.toDouble(), pos!!.y.toDouble(), pos!!.z.toDouble())
        return true
    }
}