package club.someoneice.crystaldream.client.jei

import club.someoneice.crystaldream.common.recipe.RecipeGoblins
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

class JeiRecipeGoblins(private val guiHelper: IGuiHelper): IRecipeCategory<RecipeGoblins> {
    companion object {
        val TYPE: RecipeType<RecipeGoblins> = RecipeType.create(
            CrystalDream.MODID, "goblin_type",
            RecipeGoblins::class.java
        )

        val BACKGROUND = createModPath("textures/gui/jei/tree_recipe.png")
    }


    override fun getRecipeType(): RecipeType<RecipeGoblins> = TYPE
    override fun getTitle(): Component = Component.translatable("jei.name.crystaldream.goblin")

    override fun getBackground(): IDrawable =
        guiHelper.createDrawable(BACKGROUND, 0, 0, 178, 84)

    override fun getIcon(): IDrawable =
        guiHelper.createDrawableItemStack(ModItems.TREE_TABLE.asStack())

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: RecipeGoblins, focuses: IFocusGroup) {
        var counter = 0
        for (w in 0 .. 3) {
            for (h in 1 .. 2) {
                builder.addSlot(RecipeIngredientRole.INPUT, 18 + w * 22, 20 * h + (h - 1) * 2).addIngredients(recipe.input[counter++])
            }
        }

        builder.addSlot(RecipeIngredientRole.INPUT, 106, 28).addIngredients(recipe.hand)
        builder.addSlot(RecipeIngredientRole.OUTPUT, 148, 28).addItemStack(recipe.output)
    }
}