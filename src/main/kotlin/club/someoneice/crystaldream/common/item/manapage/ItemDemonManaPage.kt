package club.someoneice.crystaldream.common.item.manapage

import club.someoneice.crystaldream.util.createAABBByRange
import club.someoneice.crystaldream.util.instance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

class ItemDemonManaPage: ItemEmptyManaPage() {
    override fun finishUsingItem(stack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack {
        level.getEntitiesOfClass(Player::class.java, createAABBByRange(livingEntity.onPos.above().center, 8)).forEach {
            it.hurt(it.damageSources().magic(), 10f)
            it.addEffect(MobEffects.POISON.instance(20 * 10, 2))
        }

        return super.finishUsingItem(stack, level, livingEntity)
    }
}