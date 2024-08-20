package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.common.item.ItemFruitPie
import club.someoneice.crystaldream.common.item.ItemGhostTerrorist
import club.someoneice.crystaldream.common.item.geo.GeoItemBlockAltar
import club.someoneice.crystaldream.common.item.geo.GeoItemBlockCrystalBall
import club.someoneice.crystaldream.core.CrystalDream
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

@Suppress("unused")
object ModItems {
    internal val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(CrystalDream.MODID)

    val CRYSTAL_POWDER: Item by ITEMS.registerSimpleItem("crystal_power")
    val GHOST_TERRORIST: Item by ITEMS.register("ghost_terrorist", ::ItemGhostTerrorist)
    val SOUL: Item by ITEMS.registerSimpleItem("deceased_soul")
    val SOUL_CRYSTAL: Item by ITEMS.registerSimpleItem("soul_crystal")
    val DREAMING_SEED: Item by ITEMS.registerSimpleItem("dream_seed")
    val DREAMING_ILLUSION: Item by ITEMS.registerSimpleItem("dreaming_illusion")

    val VERDANT_INGOT: Item by ITEMS.registerSimpleItem("verdant_ingot")
    val VERDANT_NUGGET: Item by ITEMS.registerSimpleItem("verdant_nugget")

    /* mana and magic */
    val OAK_ESS: Item by ITEMS.registerSimpleItem("oak_ess")
    val DEATH_ESS: Item by ITEMS.registerSimpleItem("death_ess")
    val END_ESS: Item by ITEMS.registerSimpleItem("end_ess")
    val WOODEN_BOTTLE: Item by ITEMS.registerSimpleItem("wooden_bottle")
    val PURE_LIVING: Item by ITEMS.registerSimpleItem("pure_living")
    val EGG: Item by ITEMS.registerSimpleItem("verdant_nugget")

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
    val MAGIC_ALTAR: Item by ITEMS.register("magic_altar", ::GeoItemBlockAltar)
    val CRYSTAL_BALL: Item by ITEMS.register("crystal_ball", ::GeoItemBlockCrystalBall)

    /* goblins */
    val FRUIT_PIE: Item by ITEMS.register("fluit_pie", ::ItemFruitPie)
    val WAND: Item by ITEMS.registerSimpleItem("wand")
}