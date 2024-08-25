package club.someoneice.crystaldream.common.item.manapage

import club.someoneice.crystaldream.core.packet.PacketSendClientMessage
import club.someoneice.crystaldream.util.createModInfo
import club.someoneice.crystaldream.util.updateNbt
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.neoforged.neoforge.network.PacketDistributor

class ItemSoulManaPage: ItemEmptyManaPage() {
    override fun finishUsingItem(stack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack {
        if (level.isClientSide() || livingEntity !is ServerPlayer) {
            return stack
        }

        stack.updateNbt {
            if (it.contains("player_soul")) {
                val playerName = it.getString("player_soul")
                livingEntity.server.playerList.getPlayerByName(playerName)?.let { player: ServerPlayer ->
                    livingEntity.teleportTo(player.level() as ServerLevel, player.x, player.y, player.z, player.yRot, player.xRot)
                }
                this.setCooldown(stack, livingEntity)
            } else {
                it.putString("player_soul", livingEntity.displayName!!.string)
                PacketDistributor.sendToPlayer(livingEntity, PacketSendClientMessage(createModInfo("success", "soul_page")))
            }
        }

        return stack
    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        if (stack.isEmpty) {
            return
        }

        stack.updateNbt {
            if (!it.contains("player_soul")) {
                return@updateNbt
            }
            tooltipComponents.add(Component.literal("Player: ${it.getString("player_soul")}"))
        }
    }
}