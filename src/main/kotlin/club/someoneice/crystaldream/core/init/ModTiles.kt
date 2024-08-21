package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.common.tile.*
import club.someoneice.crystaldream.core.CrystalDream
import com.google.common.base.Supplier
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue


object ModTiles {
    internal val TILES: DeferredRegister<BlockEntityType<*>> =
        DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CrystalDream.MODID)

    val TREE_TABLE: BlockEntityType<TileTree> by TILES.register("tree_table",
        Supplier<BlockEntityType<TileTree>> {
            BlockEntityType.Builder.of(::TileTree, ModBlocks.TREE_TABLE).build(null)
        })
    val ALTAR_MOUNT: BlockEntityType<TileMount> by TILES.register("magic_mount",
        Supplier<BlockEntityType<TileMount>> {
            BlockEntityType.Builder.of(::TileMount, ModBlocks.MAGIC_MOUNT).build(null)
        })
    val ALTAR_CORE: BlockEntityType<TileAltar> by TILES.register("magic_altar",
        Supplier<BlockEntityType<TileAltar>> {
            BlockEntityType.Builder.of(::TileAltar, ModBlocks.MAGIC_ALTAR).build(null)
        })
    val CRYSTAL_BALL: BlockEntityType<TileCrystalBall> by TILES.register("crystal_ball",
        Supplier<BlockEntityType<TileCrystalBall>> {
            BlockEntityType.Builder.of(::TileCrystalBall, ModBlocks.CRYSTAL_BALL).build(null)
        })
    val NETHER_FURNACE: BlockEntityType<TileNetherFurnace> by TILES.register("nether_furnace",
        Supplier<BlockEntityType<TileNetherFurnace>> {
            BlockEntityType.Builder.of(::TileNetherFurnace, ModBlocks.CRYSTAL_BALL).build(null)
        })
}
