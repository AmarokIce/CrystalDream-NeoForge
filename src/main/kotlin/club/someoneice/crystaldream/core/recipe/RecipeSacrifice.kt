package club.someoneice.crystaldream.core.recipe

import club.someoneice.crystaldream.core.init.ModRecipes
import net.minecraft.core.NonNullList
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.util.RecipeMatcher

data class RecipeSacrifice(
    val input: NonNullList<Ingredient> = NonNullList.withSize(8, Ingredient.EMPTY),
    val catalyst: Ingredient,
    val output: ItemStack
) {
    constructor(catalyst: Ingredient, output: ItemStack, vararg inputs: Pair<Ingredient, Int>) : this(
        ModRecipes.pairToArray(inputs), catalyst, output
    )

    init {
        if (input.size < 8 || input.any(Ingredient::isEmpty) || catalyst.isEmpty) {
            throw IllegalArgumentException("Input cannot be empty!")
        }

        if (input.size > 8) {
            val clone = input.subList(0, 8)
            input.clear()
            input.addAll(clone)
        }
    }

    fun findMatch(input: NonNullList<ItemStack>, catalyst: ItemStack): Boolean {
        if (input.size < 8 || input.any(ItemStack::isEmpty)) {
            return false
        }

        if (!this.catalyst.test(catalyst)) {
            return false
        }

        return RecipeMatcher.findMatches(input, this.input) != null
    }
}
