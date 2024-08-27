package club.someoneice.crystaldream.common.menu.slot

import club.someoneice.crystaldream.common.item.MagicCrystal
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.IItemHandler
import net.neoforged.neoforge.items.SlotItemHandler

class SlotCrystal(itemHandler: IItemHandler, index: Int, xPosition: Int, yPosition: Int) : SlotItemHandler(itemHandler, index, xPosition, yPosition) {
    override fun mayPlace(stack: ItemStack): Boolean = stack.item is MagicCrystal
    override fun mayPickup(playerIn: Player): Boolean = true
}
