package club.someoneice.crystaldream.common.tile

import club.someoneice.crystaldream.api.INetherFurnaceFuelItem
import club.someoneice.crystaldream.api.TileBase
import club.someoneice.crystaldream.common.block.BlockNetherFurnace
import club.someoneice.crystaldream.common.menu.MenuNetherFurnace
import club.someoneice.crystaldream.core.init.ModRecipes
import club.someoneice.crystaldream.core.init.ModTiles
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.items.ItemStackHandler
import kotlin.math.min

class TileNetherFurnace(pos: BlockPos, state: BlockState): TileBase(ModTiles.NETHER_FURNACE, pos, state), MenuProvider {
    /**
     * 0 - Input
     * 1 - Catalyst
     * 2 - Fuel
     * 3 - Output
     * 4 - Return Input Craft Output
     * 5 - Return Catalyst Craft Output
     * 6 - Fule Craft Output
     * 7 - Bottle
     */
    val inventory = object: ItemStackHandler(8) {
        override fun onContentsChanged(slot: Int) {
            super.onContentsChanged(slot)
            this@TileNetherFurnace.sendChanged()
        }
    }
    val data = object: ContainerData {
        override fun getCount(): Int = 3
        override fun get(index: Int): Int = when(index) {
                0 -> burnTime
                1 -> progress
                2 -> maxBurnTime
                else -> -1
            }

        override fun set(index: Int, value: Int) {
            when(index) {
                0 -> burnTime = value
                1 -> progress = value
                2 -> maxBurnTime = value
            }
        }
    }

    private var burnTime: Int = 0
    private var maxBurnTime: Int = 0
    private var progress: Int = 0

    override fun writeToNbt(nbt: CompoundTag, registries: HolderLookup.Provider) {
        nbt.putInt("burn_time", this.burnTime)
        nbt.putInt("max_burn_time", this.maxBurnTime)
        nbt.putInt("progress", this.progress)


        nbt.put("inventory", inventory.serializeNBT(registries))
    }

    override fun readFromNbt(nbt: CompoundTag, registries: HolderLookup.Provider) {
        this.burnTime = nbt.getInt("burn_time")
        this.maxBurnTime = nbt.getInt("max_burn_time")
        this.progress = nbt.getInt("progress")

        inventory.deserializeNBT(registries, nbt.getCompound("inventory"))
    }

    fun checkCanBurn(ignite: Boolean): Boolean {
        if (this.burnTime > 0) {
            burnTime--
            return true
        }

        if (!ignite) {
            return false
        }

        val item = this.inventory.getStackInSlot(2)
        if (!item.isEmpty && item.item is INetherFurnaceFuelItem) {
            if (item.hasCraftingRemainingItem()) {
                val slotIn = this.inventory.getStackInSlot(6)
                val remainingOut = item.craftingRemainingItem.copy()

                if (slotIn.isEmpty) {
                    this.inventory.setStackInSlot(6, remainingOut)
                } else {
                    val countIn = slotIn.count + remainingOut.count
                    if (ItemStack.isSameItem(slotIn, remainingOut) && countIn <= slotIn.maxStackSize) {
                        slotIn.count = countIn
                    } else {
                        return false
                    }
                }
            }

            this.burnTime = (item.item as INetherFurnaceFuelItem).getBurnTime(item)
            this.maxBurnTime = burnTime
            item.shrink(1)
            return true
        }

        return false
    }

    fun createOrBreakOutput() {
        this.progress = 0

        val output = this.inventory.getStackInSlot(3)

        val inputIn = this.inventory.getStackInSlot(0)
        val catalystIn = this.inventory.getStackInSlot(1)
        val bottle = this.inventory.getStackInSlot(7)

        if (inputIn.hasCraftingRemainingItem()) {
            insertOrDelete(4, inputIn)
        }

        if (!catalystIn.isEmpty && catalystIn.hasCraftingRemainingItem()) {
            insertOrDelete(5, catalystIn)
        }


        val recipeOutput = ModRecipes.RECIPE_OF_NETHER_FURNACE.firstOrNull {
            it.match(inputIn, catalystIn, bottle)
        }?.assemble(inputIn, catalystIn, bottle) ?: ItemStack.EMPTY

        inputIn.shrink(1)
        if (!catalystIn.isEmpty) {
            catalystIn.shrink(1)
        }

        if (recipeOutput.isEmpty) {
            return
        }

        if (output.isEmpty) {
            this.inventory.setStackInSlot(3, recipeOutput)
            return
        }
        if (!ItemStack.isSameItem(recipeOutput, output)) {
            return
        }
        output.count = min(output.maxStackSize, recipeOutput.count + output.count)
    }

    private fun insertOrDelete(slot: Int, input: ItemStack) {
        val out = this.inventory.getStackInSlot(slot)
        val outIn = input.craftingRemainingItem.copy()
        if (out.isEmpty) {
            this.inventory.setStackInSlot(slot, outIn)
        } else if (ItemStack.isSameItem(out, outIn)) {
            out.count = min(out.maxStackSize, out.count + outIn.count)
        }
    }

    companion object {
        fun tick(world: Level, pos: BlockPos, state: BlockState, tile: TileNetherFurnace) {
            val value = state.getValue(BlockNetherFurnace.LIT)

            val flag = !tile.inventory.getStackInSlot(0).isEmpty

            if (!tile.checkCanBurn(flag)) {
                tile.progress = 0
                if (value) {
                    world.setBlock(pos, state.setValue(BlockNetherFurnace.LIT, false), Block.UPDATE_CLIENTS)
                    tile.sendChanged()
                }
                return
            }

            if (!flag) {
                tile.progress = 0
                return
            }

            if (!value) {
                world.setBlock(pos, state.setValue(BlockNetherFurnace.LIT, true), Block.UPDATE_CLIENTS)
            }

            if (++tile.progress <= 260) {
                return
            }

            tile.createOrBreakOutput()
        }
    }

    override fun createMenu(id: Int, inventory: Inventory, player: Player): AbstractContainerMenu {
        return MenuNetherFurnace(id, inventory, this.blockPos, this.inventory, this.data)
    }

    override fun getDisplayName(): Component = Component.empty()

    fun sendChanged() {
        super.setChanged()
        (this.getLevel() ?: return).sendBlockUpdated(this.blockPos, this.blockState, this.blockState, Block.UPDATE_CLIENTS)
    }
}