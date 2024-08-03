package club.someoneice.crystaldream.core.recipe

import net.minecraft.core.BlockPos
import net.minecraft.core.NonNullList
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.Level

data class RecipeGoblinsRespawn(
    val input: NonNullList<Ingredient> = NonNullList.withSize(9, Ingredient.EMPTY),
    val output: ((Level, BlockPos) -> LivingEntity)? = null,
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

        output ?: throw IllegalArgumentException("Output cannot be empty!")
    }

    fun spawn(world: Level, pos: BlockPos) {
        output!!

        val entity = output.invoke(world, pos)
        entity.setPos(pos.above().center)
        world.addFreshEntity(entity)
    }
}
