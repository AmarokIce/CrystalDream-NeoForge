package club.someoneice.crystaldream.common.item

import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ItemGhost: Item(Properties().food(FoodProperties.Builder().build())) {
    override fun finishUsingItem(stack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack {
        // TODO
        return super.finishUsingItem(stack, level, livingEntity)
    }
}
