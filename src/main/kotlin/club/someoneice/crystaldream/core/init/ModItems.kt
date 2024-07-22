package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.core.CrystalDream
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredRegister
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

@Suppress("unused")
object ModItems {
    val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(CrystalDream.MODID)

    val CRYSTAL_POWDER      : Item by ITEMS.registerSimpleItem("crystal_power")
    val GHOST               : Item by ITEMS.registerSimpleItem("ghost")
    val SOUL_CRYSTAL        : Item by ITEMS.registerSimpleItem("soul_crystal")
    val DREAMING_SEED       : Item by ITEMS.registerSimpleItem("dream_seed")
    val DREAMING_ILLUSION   : Item by ITEMS.registerSimpleItem("dreaming_illusion")

    val VERDANT_INGOT       : Item by ITEMS.registerSimpleItem("verdant_ingot")
    val VERDANT_NUGGET      : Item by ITEMS.registerSimpleItem("verdant_nugget")

    val CRYSTAL_CUP         : Item by ITEMS.registerSimpleItem("crystal_cup")

}