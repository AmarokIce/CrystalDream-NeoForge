package club.someoneice.crystaldream.core.recipe

import net.minecraft.core.NonNullList
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.util.RecipeMatcher

data class RecipeSacrifice(
    val input: NonNullList<Ingredient> = NonNullList.withSize(8, Ingredient.EMPTY),
    val output: ItemStack
) {
    init {
        if (input.size > 8) {
            val clone = input.subList(0, 8)
            input.clear()
            input.addAll(clone)
        }

        if (input.any { it.isEmpty }) {
            throw IllegalArgumentException("Input cannot be empty!")
        }
    }

    fun findMatch(input: NonNullList<ItemStack>): Boolean {
        if (input.size < 8) {
            return false
        }

        if (input.any { it.isEmpty }) {
            return false
        }

        return RecipeMatcher.findMatches(input, this.input) != null
    }
}
