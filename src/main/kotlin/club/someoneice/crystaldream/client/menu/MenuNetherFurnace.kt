package club.someoneice.crystaldream.client.menu

import club.someoneice.crystaldream.client.menu.slot.SlotOutputHandler
import club.someoneice.crystaldream.common.tile.TileNetherFurnace
import club.someoneice.crystaldream.core.init.ModMenus
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.items.SlotItemHandler

class MenuNetherFurnace(containerId: Int, private val playerInv: Inventory, private val pos: BlockPos) : AbstractContainerMenu(ModMenus.NETHER_FURNACE, containerId) {
    constructor(containerId: Int, playerInv: Inventory, extraData: FriendlyByteBuf) : this(containerId, playerInv, extraData.readBlockPos())

    init {
        run {
            val world = playerInv.player.level()
            val tile = world.getBlockEntity(pos)
            if (tile !is TileNetherFurnace) {
                return@run
            }
            val capability = world.getCapability(Capabilities.ItemHandler.BLOCK, pos, Direction.UP) ?: return@run
            this.addSlot(SlotItemHandler(capability, 0, 61, 16))
            this.addSlot(SlotItemHandler(capability, 1, 41, 16))
            this.addSlot(SlotItemHandler(capability, 2, 61, 56))
            this.addSlot(SlotOutputHandler(capability, 3, 119, 36))
            this.addSlot(SlotOutputHandler(capability, 4, 12, 16))
            this.addSlot(SlotOutputHandler(capability, 5, 12, 36))
            this.addSlot(SlotOutputHandler(capability, 6, 12, 56))
            this.addSlot(SlotItemHandler(capability, 7, 91, 16))

            addPlayerInventory()
            addPlayerHotBar()
        }
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

    override fun quickMoveStack(player: Player, quickMovedSlotIndex: Int): ItemStack {
        var quickMovedStack: ItemStack  = ItemStack.EMPTY;
        val quickMovedSlot: Slot = this.slots[quickMovedSlotIndex]

        if (quickMovedSlot.hasItem()) {
            val rawStack: ItemStack = quickMovedSlot.getItem();
            quickMovedStack = rawStack.copy();

            if (quickMovedSlotIndex in 3 .. 6) {
                if (!this.moveItemStackTo(rawStack, 8, 41, true)) {
                    return ItemStack.EMPTY;
                }

                quickMovedSlot.onQuickCraft(rawStack, quickMovedStack);
            } else if (quickMovedSlotIndex in 7..43) {
                if (!this.moveItemStackTo(rawStack, 0, 3, false)
                    && !this.moveItemStackTo(rawStack, 7, 8, false)) {
                    if (quickMovedSlotIndex < 34
                        && !this.moveItemStackTo(rawStack, 34, 43, false)) {
                            return ItemStack.EMPTY;
                    } else if (!this.moveItemStackTo(rawStack, 8, 34, false)) {
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