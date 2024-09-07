package club.someoneice.crystaldream.client.jei

import club.someoneice.crystaldream.common.recipe.RecipeSacrifice
import club.someoneice.crystaldream.core.CrystalDream
import club.someoneice.crystaldream.core.init.ModItems
import club.someoneice.crystaldream.util.asStack
import club.someoneice.crystaldream.util.createModPath
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.recipe.IFocusGroup
import mezz.jei.api.recipe.RecipeIngredientRole
import mezz.jei.api.recipe.RecipeType
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.network.chat.Component

class JeiRecipeSacrifice(private val guiHelper: IGuiHelper): IRecipeCategory<RecipeSacrifice> {
    companion object {
        val TYPE: RecipeType<RecipeSacrifice> = RecipeType.create(
            CrystalDream.MODID, "sacrifice_type",
            RecipeSacrifice::class.java
        )

        val BACKGROUND = createModPath("textures/gui/jei/sacrifice_recipe.png")
    }

    override fun getRecipeType(): RecipeType<RecipeSacrifice> = TYPE
    override fun getTitle(): Component = Component.translatable("jei.name.crystaldream.sacrifice")

    override fun getBackground(): IDrawable =
        guiHelper.createDrawable(BACKGROUND, 0, 0, 179, 145)

    override fun getIcon(): IDrawable =
        guiHelper.createDrawableItemStack(ModItems.SHEPHERD_STAFF.asStack())

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: RecipeSacrifice, focuses: IFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 16, 16).addIngredients(recipe.input[0])
        builder.addSlot(RecipeIngredientRole.INPUT, 64, 16).addIngredients(recipe.input[2])
        builder.addSlot(RecipeIngredientRole.INPUT, 112, 16).addIngredients(recipe.input[1])
        builder.addSlot(RecipeIngredientRole.INPUT, 16, 64).addIngredients(recipe.input[4])
        builder.addSlot(RecipeIngredientRole.INPUT, 112, 64).addIngredients(recipe.input[3])
        builder.addSlot(RecipeIngredientRole.INPUT, 16, 112).addIngredients(recipe.input[6])
        builder.addSlot(RecipeIngredientRole.INPUT, 64, 112).addIngredients(recipe.input[5])
        builder.addSlot(RecipeIngredientRole.INPUT, 112, 112).addIngredients(recipe.input[7])

        builder.addSlot(RecipeIngredientRole.INPUT, 64, 64).addIngredients(recipe.catalyst)

        builder.addSlot(RecipeIngredientRole.OUTPUT, 150, 64).addItemStack(recipe.output)
    }
}