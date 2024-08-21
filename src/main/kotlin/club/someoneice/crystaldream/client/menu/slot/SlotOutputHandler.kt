package club.someoneice.crystaldream.client.menu.slot

import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.IItemHandler
import net.neoforged.neoforge.items.SlotItemHandler

class SlotOutputHandler(itemHandler: IItemHandler, index: Int, xPosition: Int, yPosition: Int) :
    SlotItemHandler(itemHandler, index, xPosition, yPosition) {
    override fun mayPlace(stack: ItemStack): Boolean = false
    override fun mayPickup(playerIn: Player): Boolean = true
}