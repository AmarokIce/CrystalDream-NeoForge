package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.common.tile.*
import club.someoneice.crystaldream.core.CrystalDream
import club.someoneice.crystaldream.util.createTile
import com.google.common.base.Supplier
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue


object ModTiles {
    internal val TILES: DeferredRegister<BlockEntityType<*>> =
        DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CrystalDream.MODID)

    val TREE_TABLE: BlockEntityType<TileTree> by TILES.register("tree_table",
        Supplier { createTile(::TileTree, ModBlocks.TREE_TABLE) })
    val ALTAR_MOUNT: BlockEntityType<TileMount> by TILES.register("magic_mount",
        Supplier { createTile(::TileMount, ModBlocks.MAGIC_MOUNT) })
    val ALTAR_CORE: BlockEntityType<TileAltar> by TILES.register("magic_altar",
        Supplier { createTile(::TileAltar, ModBlocks.MAGIC_ALTAR) })
    val CRYSTAL_BALL: BlockEntityType<TileCrystalBall> by TILES.register("crystal_ball",
        Supplier { createTile(::TileCrystalBall, ModBlocks.CRYSTAL_BALL) })
    val NETHER_FURNACE: BlockEntityType<TileNetherFurnace> by TILES.register("nether_furnace",
        Supplier { createTile(::TileNetherFurnace, ModBlocks.NETHER_FURNACE) })
}
