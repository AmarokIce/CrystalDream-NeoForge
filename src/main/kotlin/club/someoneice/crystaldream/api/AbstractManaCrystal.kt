package club.someoneice.crystaldream.api

import club.someoneice.crystaldream.core.init.ModCapabilities
import net.minecraft.util.Mth
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level

abstract class AbstractManaCrystal(properties: Properties) : Item(properties) {
    constructor() : this(Properties().stacksTo(1))

    abstract fun getMaxMana(): Int

    abstract fun onRecord(item: ItemStack, player: Player, world: Level): Boolean
    abstract fun onRelease(item: ItemStack, player: Player, world: Level): Boolean

    /**
     * Changed the mana in crystal.
     */
    fun addMana(item: ItemStack, mana: Int) {
        item.getCapability(ModCapabilities.CRYSTAL_MANA)?.let {
            it.setMana(Mth.clamp(it.getMana() + mana, 0, this.getMaxMana()))
        }
    }

    override fun getUseAnimation(stack: ItemStack): UseAnim = UseAnim.BOW
    override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int = 24

    override fun finishUsingItem(stack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack {
        if (livingEntity !is Player) return super.finishUsingItem(stack, level, livingEntity)
        val flag = if (livingEntity.isShiftKeyDown) {
            onRecord(stack, livingEntity, level)
        } else {
            onRelease(stack, livingEntity, level)
        }
        return if (flag) stack else super.finishUsingItem(stack, level, livingEntity)
    }
}