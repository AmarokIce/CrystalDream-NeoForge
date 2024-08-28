package club.someoneice.crystaldream.common.item.geo

import club.someoneice.crystaldream.common.item.MagicCrystal
import club.someoneice.crystaldream.util.createModPath
import club.someoneice.crystaldream.util.updateNbt
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EquipmentSlotGroup
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
            if (!player.offhandItem.isEmpty) {
                return pass
            }

            item.updateNbt {
                if (!it.contains("crystal")) {
                    return@updateNbt
                }

                val crystal = ItemStack.parseOptional(world.registryAccess(), it.getCompound("crystal"))
                player.setItemInHand(InteractionHand.OFF_HAND, crystal)
            }

            return InteractionResultHolder.success(item)
        }

        if (world.isClientSide) {
            return InteractionResultHolder.success(item)
        }

        if (player.offhandItem.isEmpty) {
            return pass
        }

        val crystal = player.offhandItem
        if (crystal.item !is MagicCrystal) {
            return pass
        }

        item.updateNbt {
            it.put("crystal", crystal.saveOptional(world.registryAccess()))
            crystal.shrink(1)
        }

        return InteractionResultHolder.success(item)
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
    override fun getAttackDamageBonus(target: Entity, damage: Float, damageSource: DamageSource): Float = 1.2f
    override fun getAnimatableInstanceCache(): AnimatableInstanceCache = cache
    override fun getUseAnimation(stack: ItemStack): UseAnim = UseAnim.BOW

    companion object {
        fun createAttributes(): ItemAttributeModifiers {
            return ItemAttributeModifiers.builder()
                .add(
                    Attributes.ATTACK_DAMAGE,
                    AttributeModifier(BASE_ATTACK_DAMAGE_ID, 4.5, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND
                ).add(
                    Attributes.ATTACK_SPEED,
                    AttributeModifier(BASE_ATTACK_SPEED_ID, -1.6, AttributeModifier.Operation.ADD_VALUE),
                    EquipmentSlotGroup.MAINHAND
                ).build()
        }
    }
}