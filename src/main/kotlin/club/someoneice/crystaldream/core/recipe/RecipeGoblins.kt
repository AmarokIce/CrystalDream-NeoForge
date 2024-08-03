package club.someoneice.crystaldream.core.recipe

import net.minecraft.core.NonNullList
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.util.RecipeMatcher

data class RecipeGoblins(
    val input: NonNullList<Ingredient> = NonNullList.withSize(9, Ingredient.EMPTY),
    val output: ItemStack = ItemStack.EMPTY,
    val outputEffect: MobEffectInstance? = null,
) {
    init {
        if (input.size > 9) {
            val clone = input.subList(0, 9)
            input.clear()
            input.addAll(clone)
        }

        if (input.any { it.isEmpty }) {
            throw IllegalArgumentException("Input cannot be empty!")
        }

        if (output.isEmpty && outputEffect == null) {
            throw IllegalArgumentException("Output cannot both be empty!")
        }
    }

    fun findMatch(input: NonNullList<ItemStack>): Boolean {
        if (input.size < 9) {
            return false
        }

        if (input.any { it.isEmpty }) {
            return false
        }

        return RecipeMatcher.findMatches(input, this.input) != null
    }
}
