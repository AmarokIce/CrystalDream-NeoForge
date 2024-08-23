package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.common.item.ItemFruitPie
import club.someoneice.crystaldream.common.item.ItemGhostTerrorist
import club.someoneice.crystaldream.common.item.ItemStableEnderPearl
import club.someoneice.crystaldream.common.item.NetherFurnaceFuelItem
import club.someoneice.crystaldream.common.item.geo.GeoItemBlockAltar
import club.someoneice.crystaldream.common.item.geo.GeoItemBlockCrystalBall
import club.someoneice.crystaldream.core.CrystalDream
import com.google.common.collect.ImmutableList
import net.minecraft.network.chat.Component
import net.minecraft.world.item.*
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

@Suppress("unused")
object ModItems {
    internal val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(CrystalDream.MODID)

    val CRYSTAL_CRAFTING_BOOK: Item by ITEMS.registerSimpleItem("crystal_crafting_book")

    val CRYSTAL_POWDER: Item by ITEMS.registerSimpleItem("crystal_power")
    val GHOST_TERRORIST: Item by ITEMS.register("ghost_terrorist", ::ItemGhostTerrorist)
    val SOUL: Item by ITEMS.registerItem("deceased_soul") { NetherFurnaceFuelItem(20 * 30) }
    val SOUL_CRYSTAL: Item by ITEMS.registerSimpleItem("soul_crystal")
    val DREAMING_SEED: Item by ITEMS.registerSimpleItem("dream_seed")
    val DREAMING_ILLUSION: Item by ITEMS.registerSimpleItem("dreaming_illusion")

    val VERDANT_INGOT: Item by ITEMS.registerSimpleItem("verdant_ingot")
    val VERDANT_NUGGET: Item by ITEMS.registerSimpleItem("verdant_nugget")

    /* mana and magic */
    val WOODEN_BOTTLE: Item by ITEMS.registerSimpleItem("wooden_jar")
    val OAK_ESS: Item by ITEMS.registerItem("oak_ess") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }
    val DEATH_ESS: Item by ITEMS.registerItem("death_ess") { NetherFurnaceFuelItem(Item.Properties().craftRemainder(WOODEN_BOTTLE), 20 * 2) }
    val END_ESS: Item by ITEMS.registerItem("end_ess") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }
    val SOUL_IN_BOTTLE: Item by ITEMS.registerItem("soul_in_bottle") { NetherFurnaceFuelItem(Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), 20 * 10) }
    val PURE_LIVING: Item by ITEMS.registerItem("pure_living") { NetherFurnaceFuelItem(Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE), 20 * 20) }
    val ORIGIN_EGG: Item by ITEMS.registerItem("origin_egg") { createItemWithTooltip(ImmutableList.of("tooltip.crystaldream.origin_egg")) }
    val STABLE_ENDER_PEARL: Item by ITEMS.register("stable_ender_pearl", ::ItemStableEnderPearl)

    val SUN_IN_BOTTLE: Item by ITEMS.registerItem("sun_in_bottle") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }
    val WIND_IN_BOTTLE: Item by ITEMS.registerItem("wind_in_bottle") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }
    val CLOUD_IN_BOTTLE: Item by ITEMS.registerItem("cloud_in_bottle") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }

    val BETRAYAL_OF_THE_WOLF: Item by ITEMS.registerItem("betrayal_of_the_wolf") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }
    val FISH_BREATHING: Item by ITEMS.registerItem("fish_breathing") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }
    val CAT_FOOTSTEPS: Item by ITEMS.registerItem("cat_footsteps") { Item(Item.Properties().craftRemainder(WOODEN_BOTTLE)) }

    /* tea */
    val CRYSTAL_CUP: Item by ITEMS.registerSimpleItem("crystal_cup")

    val TEABAG_FLOWER: Item by ITEMS.registerSimpleItem("teabag_flower")
    val TEABAG_SUGAR: Item by ITEMS.registerSimpleItem("teabag_sugar")
    val TEABAG_CRYSTAL: Item by ITEMS.registerSimpleItem("teabag_crystal")
    val TEABAG_FRUIT: Item by ITEMS.registerSimpleItem("teabag_fruit")
    val TEABAG_FIRE: Item by ITEMS.registerSimpleItem("teabag_fire")
    val TEABAG_BLOOD: Item by ITEMS.registerSimpleItem("teabag_blood")

    val TREE_TABLE: BlockItem by ITEMS.registerSimpleBlockItem("tree_table", ModBlocks::TREE_TABLE)
    val MAGIC_MOUNT: BlockItem by ITEMS.registerSimpleBlockItem("magic_mount", ModBlocks::MAGIC_MOUNT)
    val NETHER_FURNACE: BlockItem by ITEMS.registerSimpleBlockItem("nether_furnace", ModBlocks::NETHER_FURNACE)
    val MAGIC_ALTAR: Item by ITEMS.register("magic_altar", ::GeoItemBlockAltar)
    val CRYSTAL_BALL: Item by ITEMS.register("crystal_ball", ::GeoItemBlockCrystalBall)

    /* goblins */
    val FRUIT_PIE: Item by ITEMS.register("fruit_pie", ::ItemFruitPie)
    val WAND: Item by ITEMS.registerSimpleItem("wand")

    private fun createItemWithTooltip(list: ImmutableList<String>) = ModItems.createItemWithTooltip(Item.Properties(), list)

    private fun createItemWithTooltip(properties: Item.Properties, list: ImmutableList<String>) = object: Item(properties) {
        override fun appendHoverText(stack: ItemStack, context: TooltipContext, tooltipComponents: MutableList<Component>, tooltipFlag: TooltipFlag) {
            for (str in list) {
                tooltipComponents.add(Component.translatable(str))
            }
        }
    }
}