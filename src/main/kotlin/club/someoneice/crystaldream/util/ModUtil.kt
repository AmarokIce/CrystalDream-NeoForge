package club.someoneice.crystaldream.util

import club.someoneice.crystaldream.core.CrystalDream
import net.minecraft.resources.ResourceLocation

fun createModPath(path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(CrystalDream.MODID, path)
