package club.someoneice.crystaldream.common.item.manapage

import club.someoneice.crystaldream.util.createAABBByRange
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ItemHolyManaPage: ItemEmptyManaPage() {
    override fun finishUsingItem(stack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack {
        level.getEntitiesOfClass(Monster::class.java, createAABBByRange(livingEntity.onPos.above().center, 10)).forEach {
            it.hurt(it.damageSources().magic(), 10f)
            it.remainingFireTicks = 20 * 10
        }

        return super.finishUsingItem(stack, level, livingEntity)
    }
}