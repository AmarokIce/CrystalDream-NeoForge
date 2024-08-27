package club.someoneice.crystaldream.common.menu

import club.someoneice.crystaldream.core.init.ModMenus
import club.someoneice.crystaldream.util.updateNbt
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import java.util.*

class MenuShepherdStaff(containerId: Int, private val playerInv: Inventory): AbstractContainerMenu(ModMenus.NETHER_FURNACE, containerId) {
    constructor(containerId: Int, playerInv: Inventory, extraData: FriendlyByteBuf) : this(containerId, playerInv)

    val itemStack = playerInv.player.mainHandItem
    private val uuid = run {
        var uid: UUID? = null
        itemStack.updateNbt {
            if (it.contains("uuid")) {
                uid = it.getUUID("uuid")
            }
        }
        uid
    }

    override fun quickMoveStack(player: Player, quickMovedSlotIndex: Int): ItemStack {
        var quickMovedStack: ItemStack  = ItemStack.EMPTY
        val quickMovedSlot: Slot = this.slots[quickMovedSlotIndex]

        if (quickMovedSlot.hasItem()) {
            val rawStack: ItemStack = quickMovedSlot.item
            quickMovedStack = rawStack.copy()

            if (quickMovedSlotIndex < 1) {
                if (!this.moveItemStackTo(rawStack, 1, 37, true)) {
                    return ItemStack.EMPTY
                }

                quickMovedSlot.onQuickCraft(rawStack, quickMovedStack)
            } else if (quickMovedSlotIndex in 2..37) {
                if (!this.moveItemStackTo(rawStack, 0, 1, false)
                    && !this.moveItemStackTo(rawStack, 1, 28, false)) {
                    if (quickMovedSlotIndex < 34
                        && !this.moveItemStackTo(rawStack, 28, 37, false)) {
                        return ItemStack.EMPTY
                    } else if (!this.moveItemStackTo(rawStack, 1, 37, false)) {
                        return ItemStack.EMPTY
                    }
                }
            }

            else if (!this.moveItemStackTo(rawStack, 1, 37, false)) {
                return ItemStack.EMPTY
            }

            if (rawStack.isEmpty) {
                quickMovedSlot.set(ItemStack.EMPTY)
            } else {
                quickMovedSlot.setChanged()
            }

            if (rawStack.count == quickMovedStack.count) {
                return ItemStack.EMPTY
            }
            quickMovedSlot.onTake(player, rawStack)
        }

        return quickMovedStack
    }

    override fun stillValid(player: Player): Boolean = player.isAlive
            && uuid != null
            && !player.mainHandItem.isEmpty
            && (run { var uid: UUID? = null; itemStack.updateNbt {
                    if (it.contains("uuid")) { uid = it.getUUID("uuid") } }; uid } ?: false) == uuid
}