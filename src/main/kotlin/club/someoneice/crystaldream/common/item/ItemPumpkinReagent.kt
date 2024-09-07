package club.someoneice.crystaldream.common.item

import club.someoneice.crystaldream.core.init.ModEffects
import club.someoneice.crystaldream.util.instance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level

class ItemPumpkinReagent: Item(Properties()
    .stacksTo(1)
    .craftRemainder(Items.GLASS_BOTTLE)
    .food(FoodProperties.Builder()
        .nutrition(0)
        .saturationModifier(0.0f)
        .usingConvertsTo(Items.GLASS_BOTTLE)
        .effect({ MobEffects.CONFUSION.instance(20 * 5) }, 1.0f)
        .build())) {
    override fun finishUsingItem(stack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack {
        if (!livingEntity.hasEffect(ModEffects.PUMPKIN_CORRODE)) {
            return super.finishUsingItem(stack, level, livingEntity)
        }

        val effect = livingEntity.getEffect(ModEffects.PUMPKIN_CORRODE)!!
        val lv = effect.amplifier - 1

        livingEntity.removeEffect(ModEffects.PUMPKIN_CORRODE)
        if (lv > 0) {
            livingEntity.addEffect(ModEffects.PUMPKIN_CORRODE.instance(Integer.MAX_VALUE, lv))
        }

        return super.finishUsingItem(stack, level, livingEntity)
    }
}