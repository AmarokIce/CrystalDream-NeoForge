package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.core.recipe.RecipeGoblins
import club.someoneice.crystaldream.core.recipe.RecipeNetherFurnace
import club.someoneice.crystaldream.util.asIngredient
import club.someoneice.crystaldream.util.asStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.brewing.BrewingRecipe
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent
import thedarkcolour.kotlinforforge.neoforge.forge.FORGE_BUS

object ModRecipes {
    val RECIPE_OF_GOBLINS: ArrayList<RecipeGoblins> = ArrayList()
    val RECIPE_OF_NETHER_FURNACE: ArrayList<RecipeNetherFurnace> = ArrayList()

    fun init() {
        recipeGoblinsInit()
        recipeNetherFurnace()

        FORGE_BUS.addListener(::registerBrewingRecipes)
    }

    private fun recipePotion() {

    }

    private fun recipeGoblinsInit() {
        RECIPE_OF_GOBLINS.add(
            RecipeGoblins(
                ModItems.SOUL.asIngredient(), Items.LEATHER.asStack(3),
                Pair(Items.SUGAR.asIngredient(), 4), Pair(Items.ROTTEN_FLESH.asIngredient(), 4))
        )
        RECIPE_OF_GOBLINS.add(
            RecipeGoblins(
                ModItems.SOUL_CRYSTAL.asIngredient(), ModItems.ORIGIN_EGG.asStack(),
                Pair(Items.EGG.asIngredient(), 4), Pair(ModItems.PURE_LIVING.asIngredient(), 4))
        )
    }

    private fun recipeNetherFurnace() {
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                Items.OAK_SAPLING.asIngredient(), Items.BLAZE_POWDER.asIngredient(),
                ModItems.WOODEN_BOTTLE.asIngredient(), ModItems.OAK_ESS.defaultInstance)
        )
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                Items.ROTTEN_FLESH.asIngredient(), Items.BLAZE_POWDER.asIngredient(),
                ModItems.WOODEN_BOTTLE.asIngredient(), ModItems.DEATH_ESS.defaultInstance)
        )
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                Items.ENDER_EYE.asIngredient(), Items.BLAZE_POWDER.asIngredient(),
                ModItems.WOODEN_BOTTLE.asIngredient(), ModItems.END_ESS.defaultInstance)
        )
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                ModItems.SOUL_IN_BOTTLE.asIngredient(), ModItems.END_ESS.asIngredient(),
                Items.ENDER_PEARL.asIngredient(), ModItems.STABLE_ENDER_PEARL.defaultInstance)
        )
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                Items.SOUL_SAND.asIngredient(), Items.SOUL_SAND.asIngredient(),
                Ingredient.EMPTY, ModItems.SOUL.asStack(3))
        )
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                ModItems.SOUL_IN_BOTTLE.asIngredient(), ModItems.OAK_ESS.asIngredient(),
                Items.GLASS_BOTTLE.asIngredient(), ModItems.PURE_LIVING.asStack(3))
        )
    }

    private fun registerBrewingRecipes(event: RegisterBrewingRecipesEvent) {
        val builder = event.builder
        builder.addRecipe(BrewingRecipe(Items.GLASS_BOTTLE.asIngredient(), ModItems.SOUL.asIngredient(), ModItems.SOUL_IN_BOTTLE.defaultInstance))
        builder.addRecipe(BrewingRecipe(Items.AMETHYST_SHARD.asIngredient(), ModItems.PURE_LIVING.asIngredient(), ModItems.SOUL_CRYSTAL.defaultInstance))
    }
}