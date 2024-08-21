package club.someoneice.crystaldream.core.recipe

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.Ingredient

data class RecipeNetherFurnace(
    val input: Ingredient,
    val catalyst: Ingredient = Ingredient.EMPTY,
    val bottle: Ingredient = Ingredient.EMPTY,
    val output: ItemStack
) {
    fun assemble(input: ItemStack, catalyst: ItemStack = ItemStack.EMPTY, bottle: ItemStack = ItemStack.EMPTY): ItemStack {
        input.shrink(1)
        catalyst.shrink(1)
        if (!match(input, catalyst, bottle)) return this.output.copy()
        return ItemStack.EMPTY
    }

    fun match(input: ItemStack, catalyst: ItemStack, bottle: ItemStack) =
        this.input.test(input)
                && (this.catalyst.isEmpty || this.catalyst.test(catalyst)
                && (this.bottle.isEmpty || this.bottle.test(bottle)))

}
