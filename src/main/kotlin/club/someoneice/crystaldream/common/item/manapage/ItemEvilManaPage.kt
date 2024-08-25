package club.someoneice.crystaldream.common.item.manapage

import club.someoneice.crystaldream.util.createAABBByRange
import club.someoneice.crystaldream.util.instance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.animal.Animal
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ItemEvilManaPage: ItemEmptyManaPage() {
    override fun finishUsingItem(stack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack {
        level.getEntitiesOfClass(Animal::class.java, createAABBByRange(livingEntity.onPos.above().center, 10)).forEach {
            it.hurt(it.damageSources().magic(), 10f)
            it.addEffect(MobEffects.MOVEMENT_SLOWDOWN.instance(20 * 10, 1))
        }

        return super.finishUsingItem(stack, level, livingEntity)
    }
}