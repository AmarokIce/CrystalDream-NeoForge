package club.someoneice.crystaldream.client.gui

import club.someoneice.crystaldream.init.MenuInit
import club.someoneice.crystaldream.util.Util
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.Slot
import net.minecraft.world.item.ItemStack
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.items.SlotItemHandler

class ContainerCrystalMaker(pContainerId: Int, playerInventory: Inventory, val tile: TileCrystalMaker) : AbstractContainerMenu(MenuInit.CRYSTAL_MAKER, pContainerId) {
    constructor(pContainerId: Int, inv: Inventory, extraData: FriendlyByteBuf) : this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()) as TileCrystalMaker)

    val pos = tile.blockPos

    init {
        addCrystalMarkerInventory(tile)

        addPlayerInventory(playerInventory)
        addPlayerHotBar(playerInventory)
    }

    private val tileSlotCount = 9
    override fun quickMoveStack(pPlayer: Player, pIndex: Int): ItemStack {
        val sourceSlot = slots[pIndex]
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY

        val sourceStack = sourceSlot.item
        val copyOfSourceStack = sourceStack.copy()

        if (pIndex < 36) {
            if (!this.moveItemStackTo(sourceStack, 36, 36 + tileSlotCount, false))
                return ItemStack.EMPTY
        } else if (pIndex < 36 + tileSlotCount) {
            if (!this.moveItemStackTo(sourceStack, 0, 36, false))
                return ItemStack.EMPTY
        } else return ItemStack.EMPTY

        if (sourceStack.count == 0) sourceSlot.set(ItemStack.EMPTY)

        sourceSlot.setChanged()
        sourceSlot.onTake(pPlayer, sourceStack)
        return copyOfSourceStack
    }

    override fun stillValid(pPlayer: Player): Boolean {
        return !pPlayer.isDeadOrDying
    }

    private fun addCrystalMarkerInventory(tile: TileCrystalMaker) {
        tile.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent {
            this.addSlot(SlotItemHandler(it, 0, 16, 11))
            this.addSlot(SlotItemHandler(it, 1, 36, 11))
            this.addSlot(SlotItemHandler(it, 2, 16, 31))
            this.addSlot(SlotItemHandler(it, 3, 36, 31))
            this.addSlot(SlotItemHandler(it, 4, 16, 51))
            this.addSlot(SlotItemHandler(it, 5, 36, 51))

            this.addSlot(SlotItemHandler(it, 6, 64, 11))
            this.addSlot(SlotItemHandler(it, 7, 92, 11))

            this.addSlot(Util.slotOutput(it, 8, 92, 51))
        }
    }

    private fun addPlayerInventory(playerInventory: Inventory) {
        for (i in 0..2) for (l in 0..8)
            this.addSlot(Slot(playerInventory, l + i * 9 + 9, 11 + l * 18, 96 + i * 18))
    }

    private fun addPlayerHotBar(playerInventory: Inventory) {
        for (i in 0..8) this.addSlot(Slot(playerInventory, i, 11 + i * 18, 154))
    }
}
