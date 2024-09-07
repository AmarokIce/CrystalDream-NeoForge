package club.someoneice.crystaldream.core.init

import club.someoneice.crystaldream.util.createModPath
import net.minecraft.client.Minecraft
import net.minecraft.core.Holder
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.damagesource.DamageType
import net.minecraft.world.entity.LivingEntity
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn


object ModDamages {
    val CRYSTAL_REFLECTION: ResourceKey<DamageType> = createResourceKey("crystal_reflection")
    val CORRODE: ResourceKey<DamageType> = createResourceKey("corrode")

    private fun createResourceKey(name: String) = ResourceKey.create(Registries.DAMAGE_TYPE, createModPath(name))

    fun damageCrystalReflection(entity: LivingEntity) = DamageSource(typeHolder(entity, CRYSTAL_REFLECTION))
    fun damageCorrode(entity: LivingEntity) = DamageSource(typeHolder(entity, CORRODE))

    fun typeHolder(entity: LivingEntity, typeKey: ResourceKey<DamageType>) = entity.level().damageSources().damageTypes.getHolderOrThrow(typeKey)

    @OnlyIn(Dist.CLIENT) fun damageCrystalReflection() = DamageSource(typeHolder(CRYSTAL_REFLECTION))
    @OnlyIn(Dist.CLIENT) fun damageCorrode() = DamageSource(typeHolder(CORRODE))

    @OnlyIn(Dist.CLIENT)
    private fun typeHolder(typeKey: ResourceKey<DamageType>): Holder<DamageType> =
        Minecraft.getInstance().level!!.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(typeKey)
}