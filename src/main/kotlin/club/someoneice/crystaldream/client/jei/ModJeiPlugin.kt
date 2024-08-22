package club.someoneice.crystaldream.client.jei

import club.someoneice.crystaldream.core.init.ModItems
import club.someoneice.crystaldream.core.init.ModRecipes
import club.someoneice.crystaldream.util.createModPath
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.registration.IRecipeCatalystRegistration
import mezz.jei.api.registration.IRecipeCategoryRegistration
import mezz.jei.api.registration.IRecipeRegistration
import net.minecraft.resources.ResourceLocation

@JeiPlugin
class ModJeiPlugin: IModPlugin {
    override fun getPluginUid(): ResourceLocation = createModPath("crystaldream_plugin")

    override fun registerCategories(registration: IRecipeCategoryRegistration) {
        val helper = registration.jeiHelpers.guiHelper
        registration.addRecipeCategories(JeiRecipeNetherFurnace(helper))
        registration.addRecipeCategories(JeiRecipeGoblins(helper))
    }

    override fun registerRecipes(registration: IRecipeRegistration) {
        registration.addRecipes(JeiRecipeNetherFurnace.TYPE, ModRecipes.RECIPE_OF_NETHER_FURNACE)
        registration.addRecipes(JeiRecipeGoblins.TYPE, ModRecipes.RECIPE_OF_GOBLINS)
    }

    override fun registerRecipeCatalysts(registration: IRecipeCatalystRegistration) {
        registration.addRecipeCatalyst(ModItems.NETHER_FURNACE.defaultInstance, JeiRecipeNetherFurnace.TYPE)
        registration.addRecipeCatalyst(ModItems.TREE_TABLE.defaultInstance, JeiRecipeGoblins.TYPE)
    }
}