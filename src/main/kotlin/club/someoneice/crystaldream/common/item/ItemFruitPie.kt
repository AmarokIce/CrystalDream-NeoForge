package club.someoneice.crystaldream.common.item

import club.someoneice.crystaldream.common.tile.TileTree
import club.someoneice.crystaldream.core.init.ModItems
import club.someoneice.crystaldream.core.init.ModRecipes
import club.someoneice.crystaldream.core.init.ModSoundEvents
import club.someoneice.crystaldream.util.*
import net.minecraft.core.NonNullList
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.sounds.SoundSource
import net.minecraft.util.ParticleUtils
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LightningBolt
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.properties.BlockStateProperties

class ItemFruitPie : Item(
    Properties().food(
        FoodProperties.Builder()
            .nutrition(9)
            .saturationModifier(0.458f)
            .alwaysEdible()
            .effect({ MobEffects.MOVEMENT_SPEED.instance(time = 20 * 60) }, 0.85f)
            .effect({ MobEffects.HEAL.instance(time = 20 * 10) }, 0.75f)
            .build()
    )
) {
    override fun onItemUseFirst(stack: ItemStack, context: UseOnContext): InteractionResult {
        val pass = super.onItemUseFirst(stack, context)

        val player = context.player ?: return pass
        val world = context.level
        val pos = context.clickedPos
        val state = context.level.getBlockState(context.clickedPos)

        if (!state.`is`(Blocks.CAMPFIRE)) {
            return pass
        }

        if (!state.getValue(BlockStateProperties.LIT)) {
            return pass
        }

        fun spawnLightning() {
            val lightning = LightningBolt(EntityType.LIGHTNING_BOLT, world)
            lightning.setPos(player.position())
            world.addFreshEntity(lightning)
        }

        if (context.hand == InteractionHand.OFF_HAND) {
            if (!player.mainHandItem.`is`(ModItems.FRUIT_PIE)) {
                return pass
            }
        } else if (player.offhandItem.isEmpty) {
            player.sendClientDisplayMessage(createModInfo("angry_goblin.${world.random.nextIntBetweenInclusive(0, 1)}", "info"))
            spawnLightning()
            return InteractionResult.SUCCESS
        }

        player.mainHandItem.shrink(1)

        val tilePos = createBlockPosArrayWithRange(2, pos)
        val items = ArrayList<ItemStack>()

        tilePos.forEach {
            val tile = world.getBlockEntity(it)
            if (tile !is TileTree) {
                return pass
            }

            val itemInput = tile.getAndCleanItem()
            items.add(itemInput)
            if (itemInput.hasCraftingRemainingItem()) {
                tile.setItem(itemInput.craftingRemainingItem.copy())
            }
        }

        if (items.any(ItemStack::isEmpty)) {
            player.sendClientDisplayMessage(createModInfo("angry_goblin.${world.random.nextIntBetweenInclusive(0, 1)}", "info"))
            spawnLightning()
            return InteractionResult.SUCCESS
        }

        val handItem = player.offhandItem.copy().apply {
            this.count = 1
        }

        player.offhandItem.shrink(1)

        world.setBlock(pos, state.setValue(BlockStateProperties.LIT, false), 3)

        for (recipeIn in ModRecipes.RECIPE_OF_GOBLINS) {
            if (!recipeIn.findMatch(NonNullList.copyOf(items), handItem)) {
                continue
            }
            recipeIn.output.copy().asEntityAndSpawn(world, pos.x + 0.5, pos.y + 1.0, pos.z + 0.5)

            if (world.isClientSide) {
                ParticleUtils.spawnParticlesOnBlockFaces(world, pos, ParticleTypes.HAPPY_VILLAGER, UniformInt.of(2, 6))
                tilePos.forEach {
                    ParticleUtils.spawnParticlesOnBlockFaces(world, it, ParticleTypes.HAPPY_VILLAGER, UniformInt.of(1, 3))
                }
            }

            world.playSound(null, pos, ModSoundEvents.GOBLINS_HAPPY, SoundSource.BLOCKS)
            return InteractionResult.SUCCESS
        }

        player.sendClientDisplayMessage(createModInfo("angry_goblin.${world.random.nextIntBetweenInclusive(2, 4)}", "info"))
        spawnLightning()
        return InteractionResult.SUCCESS
    }
}
