package club.someoneice.crystaldream.common.tile

import club.someoneice.crystaldream.api.INetherFurnaceFuelItem
import club.someoneice.crystaldream.api.TileBase
import club.someoneice.crystaldream.core.init.ModRecipes
import club.someoneice.crystaldream.core.init.ModTiles
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.network.chat.Component
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ContainerData
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.items.wrapper.InvWrapper
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
    private val inventory = SimpleContainer(8)
    val handler = InvWrapper(inventory)
    val data = object: ContainerData {
        override fun getCount(): Int = 2
        override fun get(index: Int): Int = when(index) {
                0 -> burnTime
                1 -> progress
                else -> -1
            }

        override fun set(index: Int, value: Int) {
            when(index) {
                0 -> burnTime = value
                1 -> progress = value
            }
        }
    }

    private var burnTime: Int = 0;
    private var progress: Int = 0;

    override fun writeToNbt(nbt: CompoundTag, registries: HolderLookup.Provider) {
        nbt.putInt("burn_time", this.burnTime)
        nbt.putInt("progress", this.progress)

        nbt.put("inventory", this.inventory.createTag(registries))
    }

    override fun readFromNbt(nbt: CompoundTag, registries: HolderLookup.Provider) {
        this.burnTime = nbt.getInt("burn_time")
        this.progress = nbt.getInt("progress")

        this.inventory.fromTag(nbt.get("inventory") as ListTag, registries)
    }

    fun checkCanBurn(): Boolean {
        if (this.burnTime > 0) {
            return true
        }

        val item = this.inventory.getItem(3)
        if (!item.isEmpty && item.item is INetherFurnaceFuelItem) {
            if (item.hasCraftingRemainingItem()) {
                val slotIn = this.inventory.getItem(6)
                val remainingOut = item.craftingRemainingItem.copy()

                if (slotIn.isEmpty) {
                    this.inventory.setItem(6, remainingOut)
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
            item.shrink(1)
            return true
        }

        return false
    }

    fun createOrBreakOutput() {
        this.progress = 0

        val output = this.inventory.getItem(3)

        val inputIn = this.inventory.getItem(0)
        val catalystIn = this.inventory.getItem(1)
        val bottle = this.inventory.getItem(1)

        if (inputIn.hasCraftingRemainingItem()) {
            insertOrDelete(4, inputIn)
        }

        if (inputIn.hasCraftingRemainingItem()) {
            insertOrDelete(5, catalystIn)
        }

        val recipeOutput = (ModRecipes.RECIPE_OF_NETHER_FURNACE.firstOrNull {
            it.match(inputIn, catalystIn, this.inventory.getItem(7))
        }?.assemble(inputIn, catalystIn, this.inventory.getItem(7)) ?: ItemStack.EMPTY).copy()

        if (recipeOutput.isEmpty || ItemStack.isSameItem(recipeOutput, output)) {
            inputIn.shrink(1)
            catalystIn.shrink(1)
            return
        }

        if (output.isEmpty) {
            this.inventory.setItem(3, recipeOutput)
            return
        }

        output.count = min(output.maxStackSize, recipeOutput.count + output.count)
    }

    private fun insertOrDelete(slot: Int, input: ItemStack) {
        val out = this.inventory.getItem(slot)
        val outIn = input.craftingRemainingItem.copy()
        if (out.isEmpty) {
            this.inventory.setItem(slot, outIn)
        } else if (ItemStack.isSameItem(out, outIn)) {
            out.count = min(out.maxStackSize, out.count + outIn.count)
        }
    }

    companion object {
        fun tick(world: Level, pos: BlockPos, state: BlockState, tile: TileNetherFurnace) {
            if (world.isClientSide()) {
                return
            }

            val burnFlag = tile.burnTime > 0
            if (burnFlag) {
                tile.burnTime--
            }

            val flag = !tile.inventory.getItem(0).isEmpty
                    && !tile.inventory.getItem(1).isEmpty
                    && !tile.inventory.getItem(2).isEmpty

            if (!flag) {
                tile.progress = 0
                return
            }

            if (!tile.checkCanBurn()) {
                 return
            }

            if (++tile.progress < 260) {
                return
            }

            tile.createOrBreakOutput()
        }
    }

    override fun createMenu(p0: Int, p1: Inventory, p2: Player): AbstractContainerMenu? {
        TODO("Not yet Ananas")
    }

    override fun getDisplayName(): Component = Component.empty()
}