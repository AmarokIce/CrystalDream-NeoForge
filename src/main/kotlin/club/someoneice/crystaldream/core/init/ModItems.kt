package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.common.item.*
import club.someoneice.crystaldream.common.item.geo.GeoItemBlockAltar
import club.someoneice.crystaldream.common.item.geo.GeoItemBlockCrystalBall
import club.someoneice.crystaldream.common.item.geo.GeoItemShepherdStaff
import club.someoneice.crystaldream.common.item.manapage.*
import club.someoneice.crystaldream.core.CrystalDream
import club.someoneice.crystaldream.util.instance
import com.google.common.collect.ImmutableList
import com.mojang.serialization.Codec
import net.minecraft.network.chat.Component
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.item.*
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

@Suppress("unused")
object ModItems {
    internal val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(CrystalDream.MODID)

    val CRYSTAL_CRAFTING_BOOK: Item by ITEMS.registerSimpleItem("crystal_crafting_book")

    val CRYSTAL_POWDER: Item by ITEMS.registerSimpleItem("crystal_power")
    val GHOST_TERRORIST: Item by ITEMS.register("ghost_terrorist", ::ItemGhostTerrorist)
    val SOUL: Item by ITEMS.registerItem("deceased_soul") { BaseNetherFurnaceFuel(20 * 30) }
    val SOUL_CRYSTAL: Item by ITEMS.registerSimpleItem("soul_crystal")
    val DREAMING_SEED: Item by ITEMS.registerSimpleItem("dream_seed")
    val DREAMING_ILLUSION: Item by ITEMS.registerSimpleItem("dreaming_illusion")

    val VERDANT_INGOT: Item by ITEMS.registerSimpleItem("verdant_ingot")
    val VERDANT_NUGGET: Item by ITEMS.registerSimpleItem("verdant_nugget")

    /* mana and magic */
    val WOODEN_BOTTLE: Item by ITEMS.registerSimpleItem("wooden_jar")
    val OAK_ESS: Item by ITEMS.registerItem("oak_ess") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }
    val DEATH_ESS: Item by ITEMS.registerItem("death_ess") { BaseNetherFurnaceFuel(Item.Properties().craftRemainder(WOODEN_BOTTLE), 20 * 2) }
    val END_ESS: Item by ITEMS.registerItem("end_ess") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }
    val SOUL_IN_BOTTLE: Item by ITEMS.registerItem("soul_in_bottle") { BaseNetherFurnaceFuel(Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), 20 * 10) }
    val PURE_LIVING: Item by ITEMS.registerItem("pure_living") { BaseNetherFurnaceFuel(Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), 20 * 20) }
    val ORIGIN_EGG: Item by ITEMS.registerItem("origin_egg") { createItemWithTooltip(ImmutableList.of("tooltip.crystaldream.origin_egg")) }
    val STABLE_ENDER_PEARL: Item by ITEMS.register("stable_ender_pearl", ::ItemStableEnderPearl)
    val ENERGY_CRYSTAL: Item by ITEMS.registerSimpleItem("energy_crystal")

    val SUN_IN_BOTTLE: Item by ITEMS.registerItem("sun_in_bottle") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }
    val WIND_IN_BOTTLE: Item by ITEMS.registerItem("wind_in_bottle") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }
    val CLOUD_IN_BOTTLE: Item by ITEMS.registerItem("cloud_in_bottle") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }

    val BETRAYAL_OF_THE_WOLF: Item by ITEMS.registerItem("betrayal_of_the_wolf") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }
    val FISH_BREATHING: Item by ITEMS.registerItem("fish_breathing") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }
    val CAT_FOOTSTEPS: Item by ITEMS.registerItem("cat_footsteps") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }

    val EMPTY_MANA_PAGE: Item by ITEMS.register("empty_mana_page", ::ItemEmptyManaPage)
    val MOVING_MANA_PAGE: Item by ITEMS.register("moving_mana_page", ::ItemMovingManaPage)
    val SOUL_MANA_PAGE: Item by ITEMS.register("soul_mana_page", ::ItemSoulManaPage)
    val HOLY_MANA_PAGE: Item by ITEMS.register("holy_mana_page", ::ItemHolyManaPage)
    val EVIL_MANA_PAGE: Item by ITEMS.register("evil_mana_page", ::ItemEvilManaPage)
    val DEMON_MANA_PAGE: Item by ITEMS.register("demon_mana_page", ::ItemDemonManaPage)

    val SUN_CRYSTAL: Item by ITEMS.register("suncrystal", MultiItemMagicCrystal::SunCrystal)
    val RAIN_CRYSTAL: Item by ITEMS.register("raincrystal", MultiItemMagicCrystal::RainCrystal)
    val WIND_CRYSTAL: Item by ITEMS.register("windcrystal", MultiItemMagicCrystal::WindCrystal)
    val STORAGE_CRYSTAL: Item by ITEMS.register("storagecrystal", MultiItemMagicCrystal::StorageCrystal)
    val LIGHTNING_CRYSTAL: Item by ITEMS.register("lightningcrystal", MultiItemMagicCrystal::LightningCrystal)
    val FOX_FIRE_CRYSTAL: Item by ITEMS.register("foxfirecrystal", MultiItemMagicCrystal::FoxFireCrystal)
    val HEAL_CRYSTAL: Item by ITEMS.register("healcrystal", MultiItemMagicCrystal::HealCrystal)

    /* tea */
    val CRYSTAL_CUP: Item by ITEMS.registerSimpleItem("crystal_cup")

    val TEABAG_FLOWER: Item by ITEMS.registerSimpleItem("teabag_flower")
    val TEABAG_SUGAR: Item by ITEMS.registerSimpleItem("teabag_sugar")
    val TEABAG_CRYSTAL: Item by ITEMS.registerSimpleItem("teabag_crystal")
    val TEABAG_FRUIT: Item by ITEMS.registerSimpleItem("teabag_fruit")
    val TEABAG_FIRE: Item by ITEMS.registerSimpleItem("teabag_fire")
    val TEABAG_BLOOD: Item by ITEMS.registerSimpleItem("teabag_blood")

    val TEA_FLOWER: Item by ITEMS.registerItem("tea_flower") { ItemTea(MobEffects.HEAL.instance(20 * 5), 1.0f) }
    val TEA_SUGAR: Item by ITEMS.registerItem("tea_sugar") { ItemTea(MobEffects.MOVEMENT_SPEED.instance(20 * 20), 1.0f) }
    val TEA_CRYSTAL: Item by ITEMS.registerItem("tea_crystal") { ItemTea(MobEffects.LUCK.instance(20 * 30)) }
    val TEA_FRUIT: Item by ITEMS.registerItem("tea_fruit") { ItemTea(MobEffects.JUMP.instance(20 * 10)) }
    val TEA_FIRE: Item by ITEMS.registerItem("tea_fire") { ItemTea(MobEffects.FIRE_RESISTANCE.instance(20 * 3)) }
    val TEA_BLOOD: Item by ITEMS.registerItem("tea_blood") { ItemTea(MobEffects.HEALTH_BOOST.instance(20 * 30)) }

    val PUMPKIN_REAGENT: Item by ITEMS.register("pumpkin_reagent", ::ItemPumpkinReagent)

    /* goblins */
    val FRUIT_PIE: Item by ITEMS.register("fruit_pie", ::ItemFruitPie)
    val SHEPHERD_STAFF: Item by ITEMS.register("shepherd_staff", ::GeoItemShepherdStaff)

    val TREE_TABLE: BlockItem by ITEMS.registerSimpleBlockItem("tree_table", ModBlocks::TREE_TABLE)
    val MAGIC_MOUNT: BlockItem by ITEMS.registerSimpleBlockItem("magic_mount", ModBlocks::MAGIC_MOUNT)
    val NETHER_FURNACE: BlockItem by ITEMS.registerSimpleBlockItem("nether_furnace", ModBlocks::NETHER_FURNACE)

    val MAGIC_ALTAR: Item by ITEMS.register("magic_altar", ::GeoItemBlockAltar)
    val CRYSTAL_BALL: Item by ITEMS.register("crystal_ball", ::GeoItemBlockCrystalBall)

    private fun createItemWithTooltip(list: ImmutableList<String>) = createItemWithTooltip(Item.Properties(), list)

    private fun createItemWithTooltip(properties: Item.Properties, list: ImmutableList<String>) = object: Item(properties) {
        override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
            for (str in list) {
                tooltipComponents.add(Component.translatable(str))
            }

            Codec.INT.fieldOf("value").codec()
        }
    }
}