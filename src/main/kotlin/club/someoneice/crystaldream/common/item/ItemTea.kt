package club.someoneice.crystaldream.common.item

import club.someoneice.crystaldream.core.init.ModItems
import club.someoneice.crystaldream.util.asStack
import club.someoneice.crystaldream.util.copy
import club.someoneice.crystaldream.util.createModInfo
import club.someoneice.crystaldream.util.giveOrThrowOut
import net.minecraft.network.chat.Component
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level

class ItemTea(effect: MobEffectInstance, p: Float = 0.8f): Item(Properties().stacksTo(1)
    .craftRemainder(ModItems.CRYSTAL_CUP)
    .food(FoodProperties.Builder()
        .nutrition(1).saturationModifier(0.0f).alwaysEdible()
        .effect({ effect.copy() }, p).build())) {

    override fun getUseAnimation(stack: ItemStack): UseAnim = UseAnim.DRINK
    override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int = 24

    override fun finishUsingItem(stack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack {
        if (livingEntity is Player) {
            livingEntity.giveOrThrowOut(ModItems.CRYSTAL_CUP.asStack())
        }
        return super.finishUsingItem(stack, level, livingEntity)
    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        tooltipComponents.add(Component.translatable(createModInfo("tooltip", "tea")))
    }
}