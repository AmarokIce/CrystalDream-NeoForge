package club.someoneice.crystaldream.client.jei

import club.someoneice.crystaldream.common.recipe.RecipeNetherFurnace
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

class JeiRecipeNetherFurnace(private val guiHelper: IGuiHelper): IRecipeCategory<RecipeNetherFurnace>{
    companion object {
        val TYPE: RecipeType<RecipeNetherFurnace> = RecipeType.create(
            CrystalDream.MODID, "nether_furnace_type",
            RecipeNetherFurnace::class.java
        )

        val BACKGROUND = createModPath("textures/gui/jei/nether_furnace_background.png")
    }


    override fun getRecipeType(): RecipeType<RecipeNetherFurnace> = TYPE
    override fun getTitle(): Component = Component.translatable("jei.name.crystaldream.nether_furnace")

    override fun getBackground(): IDrawable =
        guiHelper.createDrawable(BACKGROUND, 0, 0, 177, 57)

    override fun getIcon(): IDrawable =
        guiHelper.createDrawableItemStack(ModItems.NETHER_FURNACE.asStack())

    override fun setRecipe(builder: IRecipeLayoutBuilder, recipe: RecipeNetherFurnace, focuses: IFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 21).addIngredients(recipe.catalyst)
        builder.addSlot(RecipeIngredientRole.INPUT, 48, 21).addIngredients(recipe.input)
        builder.addSlot(RecipeIngredientRole.INPUT, 130, 21).addIngredients(recipe.bottle)
        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 21).addItemStack(recipe.output)
    }

}