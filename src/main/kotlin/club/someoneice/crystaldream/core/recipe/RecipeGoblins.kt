package club.someoneice.crystaldream.core.recipe

import com.google.common.collect.ImmutableList
import net.minecraft.core.NonNullList
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.util.RecipeMatcher

data class RecipeGoblins(
    val input: NonNullList<Ingredient> = NonNullList.withSize(8, Ingredient.EMPTY),
    val hand: Ingredient = Ingredient.EMPTY,
    val output: ItemStack = ItemStack.EMPTY,
) {
    companion object {
        private fun pairToArray(pairs: Array<out Pair<Ingredient, Int>>): ImmutableList<Ingredient> {
            val list = ImmutableList.Builder<Ingredient>()
            for (pair in pairs) {
                for (i in 0 until pair.second) {
                    list.add(pair.first)
                }
            }
            return list.build()
        }
    }

    constructor(hand: Ingredient, output: ItemStack, vararg inputs: Pair<Ingredient, Int>) : this(
        NonNullList.copyOf(pairToArray(inputs)), hand, output
    )

    init {
        if (input.size > 8) {
            val clone = input.subList(0, 8)
            input.clear()
            input.addAll(clone)
        }

        if (input.any { it.isEmpty } || hand.isEmpty) {
            throw IllegalArgumentException("Input cannot be empty!")
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
