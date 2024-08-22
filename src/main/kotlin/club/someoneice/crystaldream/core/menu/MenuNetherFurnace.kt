package club.someoneice.crystaldream.core.menu

import club.someoneice.crystaldream.core.init.ModMenus
import club.someoneice.crystaldream.core.menu.slot.SlotOutputHandler
import net.minecraft.core.BlockPos
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.inventory.SimpleContainerData
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.items.IItemHandler
import net.neoforged.neoforge.items.ItemStackHandler
import net.neoforged.neoforge.items.SlotItemHandler
import kotlin.math.floor

class MenuNetherFurnace(containerId: Int, private val playerInv: Inventory, private val pos: BlockPos, itemHandler: IItemHandler, val data: ContainerData) : AbstractContainerMenu(ModMenus.NETHER_FURNACE, containerId) {
    constructor(containerId: Int, playerInv: Inventory, extraData: FriendlyByteBuf) : this(containerId, playerInv, extraData.readBlockPos(), ItemStackHandler(8), SimpleContainerData(3))

    init {
        this.addSlot(SlotItemHandler(itemHandler, 0, 61, 16))
        this.addSlot(SlotItemHandler(itemHandler, 1, 41, 16))
        this.addSlot(SlotItemHandler(itemHandler, 2, 61, 56))
        this.addSlot(SlotOutputHandler(itemHandler, 3, 119, 36))
        this.addSlot(SlotOutputHandler(itemHandler, 4, 12, 16))
        this.addSlot(SlotOutputHandler(itemHandler, 5, 12, 36))
        this.addSlot(SlotOutputHandler(itemHandler, 6, 12, 56))
        this.addSlot(SlotItemHandler(itemHandler, 7, 91, 16))

        addPlayerInventory()
        addPlayerHotBar()

        addDataSlots(this.data)
    }

    private fun addPlayerInventory() {
        for (i in 0..2) for (l in 0..8) {
            this.addSlot(Slot(playerInv, 9 + l + i * 9, 8 + l * 18, 84 + i * 18))
        }
    }

    private fun addPlayerHotBar() {
        for (i in 0..8) {
            this.addSlot(Slot(playerInv, i, 8 + i * 18, 142))
        }
    }

    fun getProgress(): Int {
        val progress = data.get(1)
        return if (progress == 0) 0 else floor(progress * (24.0 / 260.0)).toInt()
    }

    fun getBurn(): Int {
        val burn = data.get(0)
        return if (burn == 0) 0 else floor(burn * (14.0 / this.data.get(2))).toInt()
    }

    override fun quickMoveStack(player: Player, quickMovedSlotIndex: Int): ItemStack {
        var quickMovedStack: ItemStack  = ItemStack.EMPTY;
        val quickMovedSlot: Slot = this.slots[quickMovedSlotIndex]

        if (quickMovedSlot.hasItem()) {
            val rawStack: ItemStack = quickMovedSlot.getItem();
            quickMovedStack = rawStack.copy();

            if (quickMovedSlotIndex in 3 .. 6) {
                if (!this.moveItemStackTo(rawStack, 8, 43, true)) {
                    return ItemStack.EMPTY;
                }

                quickMovedSlot.onQuickCraft(rawStack, quickMovedStack);
            } else if (quickMovedSlotIndex in 7..43) {
                if (!this.moveItemStackTo(rawStack, 0, 3, false)
                    && !this.moveItemStackTo(rawStack, 7, 8, false)) {
                    if (quickMovedSlotIndex < 34
                        && !this.moveItemStackTo(rawStack, 35, 43, false)) {
                            return ItemStack.EMPTY;
                    } else if (!this.moveItemStackTo(rawStack, 8, 35, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if (!this.moveItemStackTo(rawStack, 8, 43, false)) {
                return ItemStack.EMPTY;
            }

            if (rawStack.isEmpty) {
                quickMovedSlot.set(ItemStack.EMPTY);
            } else {
                quickMovedSlot.setChanged();
            }

            if (rawStack.count == quickMovedStack.count) {
                return ItemStack.EMPTY;
            }
            quickMovedSlot.onTake(player, rawStack);
        }

        return quickMovedStack;
    }

    override fun stillValid(player: Player): Boolean {
        return player.isAlive && player.onPos.distManhattan(pos) < 15
    }
}