package club.someoneice.crystaldream.common.item.manapage

import club.someoneice.crystaldream.core.init.ModItems
import club.someoneice.crystaldream.util.setCooldown
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level

open class ItemEmptyManaPage(): Item(Properties()) {
    override fun getMaxStackSize(stack: ItemStack): Int {
        return if (stack.item == ModItems.EMPTY_MANA_PAGE) 16 else 1
    }

    override fun getUseAnimation(stack: ItemStack): UseAnim {
        return UseAnim.BOW
    }

    override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int {
        return 16
    }

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        player.startUsingItem(usedHand)
        return InteractionResultHolder.consume(player.getItemInHand(usedHand))
    }

    override fun finishUsingItem(stack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack {
        if (livingEntity is Player) {
            this.setCooldown(stack, livingEntity)
        }
        return stack
    }

    protected fun setCooldown(item: ItemStack, player: Player) {
        item.setCooldown(player, 20 * 60)
    }
}