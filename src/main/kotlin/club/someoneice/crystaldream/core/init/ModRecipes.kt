package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.core.recipe.RecipeGoblins
import club.someoneice.crystaldream.core.recipe.RecipeNetherFurnace
import club.someoneice.crystaldream.core.recipe.RecipeSacrifice
import club.someoneice.crystaldream.util.asIngredient
import club.someoneice.crystaldream.util.asStack
import net.minecraft.core.NonNullList
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.neoforged.neoforge.common.brewing.BrewingRecipe
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent

object ModRecipes {
    val RECIPE_OF_GOBLINS: ArrayList<RecipeGoblins> = ArrayList()
    val RECIPE_OF_NETHER_FURNACE: ArrayList<RecipeNetherFurnace> = ArrayList()
    val RECIPE_OF_SACRIFICE: ArrayList<RecipeSacrifice> = ArrayList()

    fun init() {
        recipeGoblinsInit()
        respawnRecipe()
        recipeNetherFurnace()
        recipeSacrifice()
    }

    private fun recipeGoblinsInit() {
        RECIPE_OF_GOBLINS.add(
            RecipeGoblins(
                ModItems.SOUL.asIngredient(), Items.LEATHER.asStack(3),
                Pair(Items.SUGAR.asIngredient(), 4), Pair(Items.ROTTEN_FLESH.asIngredient(), 4)))
        RECIPE_OF_GOBLINS.add(
            RecipeGoblins(
                ModItems.SOUL_CRYSTAL.asIngredient(), ModItems.ORIGIN_EGG.asStack(),
                Pair(Items.EGG.asIngredient(), 4), Pair(ModItems.PURE_LIVING.asIngredient(), 4)))
        RECIPE_OF_GOBLINS.add(
            RecipeGoblins(
                ModItems.WOODEN_BOTTLE.asIngredient(), ModItems.SUN_IN_BOTTLE.asStack(),
                Pair(Items.SUNFLOWER.asIngredient(), 4), Pair(ModItems.OAK_ESS.asIngredient(), 4)))
        RECIPE_OF_GOBLINS.add(
            RecipeGoblins(
                ModItems.WOODEN_BOTTLE.asIngredient(), ModItems.WIND_IN_BOTTLE.asStack(),
                Pair(Items.WIND_CHARGE.asIngredient(), 4), Pair(ModItems.OAK_ESS.asIngredient(), 4)))
        RECIPE_OF_GOBLINS.add(
            RecipeGoblins(
                Items.CAT_SPAWN_EGG.asIngredient(), ModItems.CAT_FOOTSTEPS.asStack(),
                Pair(Items.COD.asIngredient(), 4), Pair(ModItems.SOUL.asIngredient(), 4)))
    }

    private fun recipeNetherFurnace() {
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                Items.OAK_SAPLING.asIngredient(), Items.BLAZE_POWDER.asIngredient(),
                ModItems.WOODEN_BOTTLE.asIngredient(), ModItems.OAK_ESS.defaultInstance))
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                Items.ROTTEN_FLESH.asIngredient(), Items.BLAZE_POWDER.asIngredient(),
                ModItems.WOODEN_BOTTLE.asIngredient(), ModItems.DEATH_ESS.defaultInstance))
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                Items.ENDER_EYE.asIngredient(), Items.BLAZE_POWDER.asIngredient(),
                ModItems.WOODEN_BOTTLE.asIngredient(), ModItems.END_ESS.defaultInstance))
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                ModItems.SOUL_IN_BOTTLE.asIngredient(), ModItems.END_ESS.asIngredient(),
                Items.ENDER_PEARL.asIngredient(), ModItems.STABLE_ENDER_PEARL.defaultInstance))
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                Items.SOUL_SAND.asIngredient(), Items.SOUL_SAND.asIngredient(),
                Ingredient.EMPTY, ModItems.SOUL.asStack(3)))
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                ModItems.SOUL_IN_BOTTLE.asIngredient(), ModItems.OAK_ESS.asIngredient(),
                Items.GLASS_BOTTLE.asIngredient(), ModItems.PURE_LIVING.asStack()))
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                ModItems.SOUL.asIngredient(), Ingredient.EMPTY,
                Items.DIRT.asIngredient(), Items.SOUL_SOIL.asStack()))
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                ModItems.SOUL.asIngredient(), Ingredient.EMPTY,
                Items.SAND.asIngredient(), Items.SOUL_SAND.asStack()))
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                ModItems.DEATH_ESS.asIngredient(), ModItems.OAK_ESS.asIngredient(),
                Items.ROTTEN_FLESH.asIngredient(), ModItems.SOUL.asStack()))
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                Items.WATER_BUCKET.asIngredient(), ModItems.SUN_IN_BOTTLE.asIngredient(),
                ModItems.WOODEN_BOTTLE.asIngredient(), ModItems.CLOUD_IN_BOTTLE.asStack()))
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                Items.WOLF_SPAWN_EGG.asIngredient(), ModItems.SOUL_CRYSTAL.asIngredient(),
                ModItems.WOODEN_BOTTLE.asIngredient(), ModItems.BETRAYAL_OF_THE_WOLF.asStack()))
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                Items.COD_BUCKET.asIngredient(), ModItems.SOUL_CRYSTAL.asIngredient(),
                ModItems.WOODEN_BOTTLE.asIngredient(), ModItems.FISH_BREATHING.asStack()))
        RECIPE_OF_NETHER_FURNACE.add(
            RecipeNetherFurnace(
                Items.GOLD_INGOT.asIngredient(), ModItems.SOUL_CRYSTAL.asIngredient(),
                Items.DIAMOND.asIngredient(), ModItems.ENERGY_CRYSTAL.asStack()))
    }

    private fun recipeSacrifice() {
        RECIPE_OF_SACRIFICE.add(RecipeSacrifice(ModItems.SOUL_CRYSTAL.asIngredient(), ModItems.EMPTY_MANA_PAGE.asStack(),
                Pair(Items.PAPER.asIngredient(), 4), Pair(ModItems.CRYSTAL_POWDER.asIngredient(), 4)))
        RECIPE_OF_SACRIFICE.add(RecipeSacrifice(ModItems.EMPTY_MANA_PAGE.asIngredient(), ModItems.MOVING_MANA_PAGE.asStack(),
            Pair(ModItems.CRYSTAL_POWDER.asIngredient(), 4), Pair(ModItems.STABLE_ENDER_PEARL.asIngredient(), 4)))
        RECIPE_OF_SACRIFICE.add(RecipeSacrifice(ModItems.MOVING_MANA_PAGE.asIngredient(), ModItems.SOUL_MANA_PAGE.asStack(),
            Pair(ModItems.PURE_LIVING.asIngredient(), 4), Pair(ModItems.BETRAYAL_OF_THE_WOLF.asIngredient(), 4)))
        RECIPE_OF_SACRIFICE.add(RecipeSacrifice(ModItems.EMPTY_MANA_PAGE.asIngredient(), ModItems.HOLY_MANA_PAGE.asStack(),
            Pair(ModItems.PURE_LIVING.asIngredient(), 4), Pair(ModItems.SUN_IN_BOTTLE.asIngredient(), 4)))
        RECIPE_OF_SACRIFICE.add(RecipeSacrifice(ModItems.EMPTY_MANA_PAGE.asIngredient(), ModItems.EVIL_MANA_PAGE.asStack(),
            Pair(ModItems.SOUL_IN_BOTTLE.asIngredient(), 4), Pair(ModItems.CLOUD_IN_BOTTLE.asIngredient(), 4)))
        RECIPE_OF_SACRIFICE.add(RecipeSacrifice(ModItems.EMPTY_MANA_PAGE.asIngredient(), ModItems.DEMON_MANA_PAGE.asStack(),
            Pair(ModItems.SOUL.asIngredient(), 4), Pair(ModItems.WIND_IN_BOTTLE.asIngredient(), 4)))
    }

    private fun respawnRecipe() {
        RECIPE_OF_GOBLINS.add(
            RecipeGoblins(
                ModItems.ORIGIN_EGG.asIngredient(), Items.SHEEP_SPAWN_EGG.asStack(),
                Pair(Items.WHITE_WOOL.asIngredient(), 4), Pair(ModItems.PURE_LIVING.asIngredient(), 2),
                Pair(Items.MUTTON.asIngredient(), 2)))
        RECIPE_OF_GOBLINS.add(
            RecipeGoblins(
                ModItems.ORIGIN_EGG.asIngredient(), Items.COW_SPAWN_EGG.asStack(),
                Pair(Items.LEATHER.asIngredient(), 4), Pair(ModItems.PURE_LIVING.asIngredient(), 2),
                Pair(Items.BEEF.asIngredient(), 2)))
        RECIPE_OF_GOBLINS.add(
            RecipeGoblins(
                ModItems.ORIGIN_EGG.asIngredient(), Items.CHICKEN_SPAWN_EGG.asStack(),
                Pair(Items.FEATHER.asIngredient(), 4), Pair(ModItems.PURE_LIVING.asIngredient(), 2),
                Pair(Items.CHICKEN.asIngredient(), 2)))
        RECIPE_OF_GOBLINS.add(
            RecipeGoblins(
                ModItems.ORIGIN_EGG.asIngredient(), Items.PIG_SPAWN_EGG.asStack(),
                Pair(ModItems.PURE_LIVING.asIngredient(), 2), Pair(Items.PORKCHOP.asIngredient(), 6)))
        RECIPE_OF_GOBLINS.add(
            RecipeGoblins(
                ModItems.ORIGIN_EGG.asIngredient(), Items.CAT_SPAWN_EGG.asStack(),
                Pair(Items.COD.asIngredient(), 4), Pair(ModItems.PURE_LIVING.asIngredient(), 4)))
        RECIPE_OF_GOBLINS.add(
            RecipeGoblins(
                ModItems.ORIGIN_EGG.asIngredient(), Items.WOLF_SPAWN_EGG.asStack(),
                Pair(Items.BONE.asIngredient(), 4), Pair(ModItems.PURE_LIVING.asIngredient(), 4)))
    }

    internal fun registerBrewingRecipes(event: RegisterBrewingRecipesEvent) {
        val builder = event.builder

        builder.addRecipe(BrewingRecipe(Items.GLASS_BOTTLE.asIngredient(), ModItems.SOUL.asIngredient(), ModItems.SOUL_IN_BOTTLE.defaultInstance))
        builder.addRecipe(BrewingRecipe(Items.AMETHYST_SHARD.asIngredient(), ModItems.PURE_LIVING.asIngredient(), ModItems.SOUL_CRYSTAL.defaultInstance))

        builder.addRecipe(BrewingRecipe(ModItems.CRYSTAL_CUP.asIngredient(), ModItems.TEABAG_FRUIT.asIngredient(), ModItems.TEA_FRUIT.defaultInstance))
        builder.addRecipe(BrewingRecipe(ModItems.CRYSTAL_CUP.asIngredient(), ModItems.TEABAG_CRYSTAL.asIngredient(), ModItems.TEA_CRYSTAL.defaultInstance))
        builder.addRecipe(BrewingRecipe(ModItems.CRYSTAL_CUP.asIngredient(), ModItems.TEABAG_SUGAR.asIngredient(), ModItems.TEA_SUGAR.defaultInstance))
        builder.addRecipe(BrewingRecipe(ModItems.CRYSTAL_CUP.asIngredient(), ModItems.TEABAG_FLOWER.asIngredient(), ModItems.TEA_FLOWER.defaultInstance))
        builder.addRecipe(BrewingRecipe(ModItems.CRYSTAL_CUP.asIngredient(), ModItems.TEABAG_FIRE.asIngredient(), ModItems.TEA_FIRE.defaultInstance))
        builder.addRecipe(BrewingRecipe(ModItems.CRYSTAL_CUP.asIngredient(), ModItems.TEABAG_BLOOD.asIngredient(), ModItems.TEA_BLOOD.defaultInstance))
    }

    internal fun pairToArray(pairs: Array<out Pair<Ingredient, Int>>): NonNullList<Ingredient> {
        val list = java.util.ArrayList<Ingredient>()
        pairs.forEach {
            for (i in 0 until it.second) {
                list.add(it.first)
            }
        }
        return NonNullList.copyOf(list)
    }
}