package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.core.recipe.RecipeGoblins
import club.someoneice.crystaldream.core.recipe.RecipeNetherFurnace
import club.someoneice.crystaldream.util.asIngredient
import club.someoneice.crystaldream.util.asStack
import net.minecraft.world.item.Items

object ModRecipes {
    val RECIPE_OF_GOBLINS: ArrayList<RecipeGoblins> = ArrayList()
    val RECIPE_OF_NETHER_FURNACE: ArrayList<RecipeNetherFurnace> = ArrayList()

    fun init() {
        recipeGoblinsInit()

    }

    private fun recipePotion() {

    }

    private fun recipeGoblinsInit() {
        RECIPE_OF_GOBLINS.add(
            RecipeGoblins(
                ModItems.SOUL.asIngredient(), Items.LEATHER.asStack(3),
                Pair(Items.SUGAR.asIngredient(), 4), Pair(Items.ROTTEN_FLESH.asIngredient(), 4)
            )
        )
    }

    private fun recipeGoblinsRespawnInit() {

    }
}