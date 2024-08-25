package club.someoneice.crystaldream.common.item.manapage

import club.someoneice.crystaldream.core.packet.PacketSendClientMessage
import club.someoneice.crystaldream.util.createModInfo
import club.someoneice.crystaldream.util.updateNbt
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.neoforged.neoforge.network.PacketDistributor

class ItemMovingManaPage: ItemEmptyManaPage() {
    override fun finishUsingItem(stack: ItemStack, level: Level, livingEntity: LivingEntity): ItemStack {
        if (level.isClientSide() || livingEntity !is ServerPlayer) {
            return stack
        }

        stack.updateNbt {
            if (it.contains("pos_on") && it.contains("world")) {
                val pos = BlockPos.of(it.getLong("pos_on"))
                val rl = ResourceLocation.parse(it.getString("world"))
                val world = livingEntity.server.getLevel(ResourceKey.create(Registries.DIMENSION, rl))
                world?.let {
                    livingEntity.teleportTo(
                        world,
                        pos.x.toDouble(),
                        pos.y.toDouble(),
                        pos.z.toDouble(),
                        livingEntity.getYRot(),
                        livingEntity.getXRot())
                    this.setCooldown(stack, livingEntity)
                    return@updateNbt
                }

                PacketDistributor.sendToPlayer(livingEntity, PacketSendClientMessage(createModInfo("fail", "moving_mana_page")))
                return@updateNbt
            }

            if (livingEntity.isShiftKeyDown) {
                it.remove("pos_on")
                it.remove("world")
                PacketDistributor.sendToPlayer(livingEntity, PacketSendClientMessage("clean.crystaldream.stable_ender_pearl"))
                return@updateNbt
            }

            it.putLong("pos_on", livingEntity.onPos.above().asLong())
            it.putString("world", livingEntity.level().dimension().location().toString())
            PacketDistributor.sendToPlayer(livingEntity, PacketSendClientMessage("success.crystaldream.stable_ender_pearl"))
        }

        return stack
    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        if (stack.isEmpty) {
            return
        }

        stack.updateNbt {
            if (!it.contains("pos_on") || !it.contains("world")) {
                return@updateNbt
            }

            val pos = BlockPos.of(it.getLong("pos_on"))
            val rl = it.getString("world")

            tooltipComponents.add(Component.literal("x: ${pos.x}, y: ${pos.y}, z: ${pos.z}"))
            tooltipComponents.add(Component.literal("World: $rl"))
        }
    }
}