package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.common.block.*
import club.someoneice.crystaldream.core.CrystalDream
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

object ModBlocks {
    internal val BLOCKS: DeferredRegister.Blocks = DeferredRegister.createBlocks(CrystalDream.MODID)

    val TREE_TABLE: BlockTree by BLOCKS.register("tree_table", ::BlockTree)
    val MAGIC_MOUNT: BlockMount by BLOCKS.register("magic_mount", ::BlockMount)
    val MAGIC_ALTAR: BlockAltar by BLOCKS.register("magic_altar", ::BlockAltar)
    val CRYSTAL_BALL: BlockCrystalBall by BLOCKS.register("crystal_ball", ::BlockCrystalBall)
    val NETHER_FURNACE: BlockNetherFurnace by BLOCKS.register("crystal_ball", ::BlockNetherFurnace)

    val VERDANT_BLOCK: Block by BLOCKS.registerSimpleBlock("verdant_block", BlockBehaviour.Properties.of())
    val VERDANT_RAW_ORE_BLOCK: Block by BLOCKS.registerSimpleBlock(
        "verdant_raw_ore_block",
        BlockBehaviour.Properties.of()
    )
}
