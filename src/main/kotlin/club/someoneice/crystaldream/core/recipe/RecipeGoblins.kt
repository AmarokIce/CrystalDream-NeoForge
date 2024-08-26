package club.someoneice.crystaldream.core.recipe

import club.someoneice.crystaldream.core.init.ModRecipes
import net.minecraft.core.NonNullList
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.util.RecipeMatcher

data class RecipeGoblins(
    val input: NonNullList<Ingredient> = NonNullList.withSize(8, Ingredient.EMPTY),
    val hand: Ingredient = Ingredient.EMPTY,
    val output: ItemStack = ItemStack.EMPTY,
) {
    constructor(hand: Ingredient, output: ItemStack, vararg inputs: Pair<Ingredient, Int>) : this(
        ModRecipes.pairToArray(inputs), hand, output
    )

    init {
        if (input.size < 8 || input.any(Ingredient::isEmpty) || hand.isEmpty) {
            throw IllegalArgumentException("Input cannot be empty!")
        }

        if (input.size > 8) {
            val clone = input.subList(0, 8)
            input.clear()
            input.addAll(clone)
        }
    }

    fun findMatch(input: NonNullList<ItemStack>, hand: ItemStack): Boolean {
        if (input.size < 8) {
            return false
        }

        if (!this.hand.test(hand)) {
            return false
        }

        if (input.any { it.isEmpty }) {
            return false
        }

        return RecipeMatcher.findMatches(input, this.input) != null
    }
}
