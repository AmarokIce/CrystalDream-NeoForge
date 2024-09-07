package club.someoneice.crystaldream.common.item.geo

import club.someoneice.crystaldream.common.item.MultiItemMagicCrystal
import club.someoneice.crystaldream.core.CrystalDream
import club.someoneice.crystaldream.util.createModPath
import club.someoneice.crystaldream.util.spawnCirclePos
import club.someoneice.crystaldream.util.updateNbt
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlotGroup
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.AttributeModifier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.UseAnim
import net.minecraft.world.item.component.ItemAttributeModifiers
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.fml.loading.FMLEnvironment
import software.bernie.geckolib.animatable.GeoItem
import software.bernie.geckolib.animatable.client.GeoRenderProvider
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.animation.AnimatableManager
import software.bernie.geckolib.model.GeoModel
import software.bernie.geckolib.renderer.GeoItemRenderer
import software.bernie.geckolib.util.GeckoLibUtil
import java.util.function.Consumer


class GeoItemShepherdStaff: Item(Properties().stacksTo(1).attributes(createAttributes())), GeoItem {
    private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    override fun createGeoRenderer(consumer: Consumer<GeoRenderProvider>) {
        consumer.accept(object : GeoRenderProvider {
            private val renderer: BlockEntityWithoutLevelRenderer =
                object : GeoItemRenderer<GeoItemShepherdStaff>(object :
                    GeoModel<GeoItemShepherdStaff>() {
                        override fun getModelResource(animatable: GeoItemShepherdStaff): ResourceLocation =
                            createModPath("geo/shepherd.geo.json")

                        override fun getTextureResource(animatable: GeoItemShepherdStaff): ResourceLocation =
                            createModPath("textures/geo/shepherd.png")

                        override fun getAnimationResource(animatable: GeoItemShepherdStaff): ResourceLocation =
                            createModPath("animations/shepherd.animation.json")
                    }) {
                    override fun getRenderType(
                        animatable: GeoItemShepherdStaff?,
                        texture: ResourceLocation?,
                        bufferSource: MultiBufferSource?,
                        partialTick: Float
                    ): RenderType? =
                        RenderType.entityTranslucent(getTextureLocation(animatable))
                }

            override fun getGeoItemRenderer(): BlockEntityWithoutLevelRenderer {
                return renderer
            }
        })
    }

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
    }

    override fun use(world: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        val pass = super.use(world, player, usedHand)
        if (usedHand == InteractionHand.OFF_HAND) {
            return pass
        }

        val item = player.getItemInHand(usedHand)

        if (!player.isShiftKeyDown) {
            player.startUsingItem(usedHand)
            return InteractionResultHolder.pass(item)
        }

        item.updateNbt {
            if (it.contains("crystal")) {
                if (!player.offhandItem.isEmpty) {
                    return@updateNbt
                }

                val crystal = ItemStack.parseOptional(world.registryAccess(), it.getCompound("crystal"))
                player.setItemInHand(InteractionHand.OFF_HAND, crystal)
                it.remove("crystal")

                return@updateNbt
            }

            if (player.offhandItem.isEmpty) {
                return@updateNbt
            }

            val crystal = player.offhandItem
            if (crystal.item !is MultiItemMagicCrystal) {
                return@updateNbt
            }

            it.put("crystal", crystal.saveOptional(world.registryAccess()))
            crystal.shrink(1)
        }

        return InteractionResultHolder.success(item)
    }

    override fun onUseTick(world: Level, livingEntity: LivingEntity, stack: ItemStack, remainingUseDuration: Int) {
        if (!world.isClientSide()) {
            return
        }

        for (pos in spawnCirclePos(livingEntity.position(), 1.2, 16)) {
            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, pos.x, pos.y, pos.z, 0.0, 0.55, 0.0)
        }
    }

    override fun finishUsingItem(stack: ItemStack, world: Level, player: LivingEntity): ItemStack {
        if (world.isClientSide() || player !is ServerPlayer) {
            return stack
        }
        stack.updateNbt {
            if (!it.contains("crystal")) {
                return@updateNbt
            }

            val crystal = ItemStack.parseOptional(world.registryAccess(), it.getCompound("crystal"))
            if (crystal.item !is MultiItemMagicCrystal) {
                CrystalDream.LOGGER.error("The item in Shepherd Staff is not a Magic Crystal!")
                return@updateNbt
            }

            (crystal.item as MultiItemMagicCrystal).work(stack, crystal, world as ServerLevel, player)
        }
        player.cooldowns.addCooldown(stack.item, 20 * 5)
        return stack
    }

    override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
        if (!FMLEnvironment.dist.isClient || stack.isEmpty) {
            return
        }

        val world = Minecraft.getInstance().level!!
        var crystal: ItemStack = ItemStack.EMPTY
        stack.updateNbt {
            if (!it.contains("crystal")) {
                return@updateNbt
            }

            crystal = ItemStack.parseOptional(world.registryAccess(), it.getCompound("crustal"))
        }

        if (!crystal.isEmpty) {
            tooltipComponents.add(crystal.displayName)
        }
    }

    override fun canAttackBlock(state: BlockState, level: Level, pos: BlockPos, player: Player): Boolean = false
    override fun getAttackDamageBonus(target: Entity, damage: Float, damageSource: DamageSource): Float = target.level().random.nextFloat() * 2.0f
    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = cache
    override fun getUseAnimation(stack: ItemStack): UseAnim = UseAnim.BOW
    override fun getUseDuration(stack: ItemStack, entity: LivingEntity): Int = 48

    companion object {
        fun createAttributes(): ItemAttributeModifiers {
            return ItemAttributeModifiers.builder()
                .add(
                    Attributes.ATTACK_DAMAGE,
                    AttributeModifier(BASE_ATTACK_DAMAGE_ID, 3.5, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND
                ).add(
                    Attributes.ATTACK_SPEED,
                    AttributeModifier(BASE_ATTACK_SPEED_ID, -2.6, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND
                ).build()
        }
    }
}