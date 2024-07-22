package club.someoneice.crystaldream.util

import club.someoneice.crystaldream.core.copy
import club.someoneice.crystaldream.core.giveOrThrowOut
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level

object ItemUtil {
    fun item(maxSize: Int = 64, canRepair: Boolean = true): Item {
        val properties = Item.Properties()
        properties.stacksTo(maxSize)
        if (!canRepair) properties.setNoRepair()

        return Item(properties)
    }

    fun itemFood(
        hunger: Int,
        saturation: Float,
        fast: Boolean = false,
        alwaysEat: Boolean = false,
        maxSize: Int = 64,
        isDrink: Boolean = false,
        itemReturn: ItemStack = ItemStack.EMPTY,
        vararg effects: MobEffectInstance
    ): Item {
        val builder = FoodProperties.Builder()
        builder.nutrition(hunger).saturationModifier(saturation)
        if (fast) builder.fast()
        if (alwaysEat) builder.alwaysEdible()

        val properties = Item.Properties()
        properties.stacksTo(maxSize).food(builder.build())

        return object : Item(properties) {
            override fun getUseAnimation(stack: ItemStack) = if (isDrink) UseAnim.DRINK else UseAnim.EAT

            override fun finishUsingItem(stack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack {
                if (effects.isNotEmpty()) effects.forEach { livingEntity.addEffect(it.copy()) }
                if (!itemReturn.isEmpty && livingEntity is Player) livingEntity.giveOrThrowOut(itemReturn)
                return super.finishUsingItem(stack, level, livingEntity)
            }
        }
    }
}