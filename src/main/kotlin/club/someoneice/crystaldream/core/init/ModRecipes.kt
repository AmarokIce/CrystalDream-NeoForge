package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.core.recipe.RecipeGoblins
import club.someoneice.crystaldream.core.recipe.RecipeGoblinsRespawn
import club.someoneice.crystaldream.util.asIngredient
import club.someoneice.crystaldream.util.asStack
import net.minecraft.core.NonNullList
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient

object ModRecipes {
    val RecipeGoblins: ArrayList<RecipeGoblins> = ArrayList()
    val RecipeGoblinsRespawn: ArrayList<RecipeGoblinsRespawn> = ArrayList()

    fun init() {
        recipeGoblinsInit()

    }

    private fun recipePotion() {

    }

    private fun recipeGoblinsInit() {
        RecipeGoblins.add(
            RecipeGoblins(
                NonNullList.of(
                    Ingredient.EMPTY,
                    Items.ROTTEN_FLESH.asIngredient(),
                    Items.ROTTEN_FLESH.asIngredient(),
                    Items.ROTTEN_FLESH.asIngredient(),
                    Items.ROTTEN_FLESH.asIngredient(),
                    Items.SUGAR.asIngredient(),
                    Items.SUGAR.asIngredient(),
                    Items.SUGAR.asIngredient(),
                    Items.SUGAR.asIngredient(),
                    ModItems.SOUL.asIngredient()
                ), Items.LEATHER.asStack(3)
            )
        )
    }

    private fun recipeGoblinsRespawnInit() {

    }
}