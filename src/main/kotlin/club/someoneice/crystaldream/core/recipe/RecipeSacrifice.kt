package club.someoneice.crystaldream.core.recipe

import net.minecraft.core.NonNullList
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.util.RecipeMatcher

data class RecipeSacrifice(
    val input: NonNullList<Ingredient> = NonNullList.withSize(8, Ingredient.EMPTY),
    val catalyst: Ingredient,
    val output: ItemStack
) {
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
