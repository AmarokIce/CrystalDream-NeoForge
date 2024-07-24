package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.common.tile.TileAltar
import club.someoneice.crystaldream.common.tile.TileCrystalBall
import club.someoneice.crystaldream.common.tile.TileMount
import club.someoneice.crystaldream.common.tile.TileTree
import club.someoneice.crystaldream.core.CrystalDream
import com.google.common.base.Supplier
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue


object ModTiles {
    internal val TILES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CrystalDream.MODID)

    val TREE_TABLE: BlockEntityType<TileTree> by TILES.register("tree_table",
        object: Supplier<BlockEntityType<TileTree>> {
            override fun get() = BlockEntityType.Builder.of(::TileTree, ModBlocks.TREE_TABLE).build(null)
    } )
    val ALTAR_MOUNT: BlockEntityType<TileMount> by TILES.register("magic_mount",
        object: Supplier<BlockEntityType<TileMount>> {
            override fun get() = BlockEntityType.Builder.of(::TileMount, ModBlocks.MAGIC_MOUNT).build(null)
    })
    val ALTAR_CORE: BlockEntityType<TileAltar> by TILES.register("magic_altar",
        object: Supplier<BlockEntityType<TileAltar>> {
            override fun get() = BlockEntityType.Builder.of(::TileAltar, ModBlocks.MAGIC_ALTAR).build(null)
    })
    val CRYSTAL_BALL: BlockEntityType<TileCrystalBall> by TILES.register("crystal_ball",
        object: Supplier<BlockEntityType<TileCrystalBall>> {
            override fun get() = BlockEntityType.Builder.of(::TileCrystalBall, ModBlocks.CRYSTAL_BALL).build(null)
    })
}
