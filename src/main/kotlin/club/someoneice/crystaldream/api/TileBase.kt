package club.someoneice.crystaldream.api

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

abstract class TileBase(type: BlockEntityType<*>?, pos: BlockPos?, state: BlockState?) : BlockEntity(type, pos, state) {
    abstract fun writeToNbt(nbt: CompoundTag, registries: HolderLookup.Provider)
    abstract fun readFromNbt(nbt: CompoundTag, registries: HolderLookup.Provider)

    fun markDirt() {
        this.getLevel()?.let {
            if (it.isClientSide()) return@markDirt
            it.sendBlockUpdated(this.blockPos, this.blockState, this.blockState, 3)
            (it as ServerLevel).players().forEach { player ->
                player.connection.send(this.updatePacket)
            }
        }
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    override fun handleUpdateTag(tag: CompoundTag, lookupProvider: HolderLookup.Provider) {
        super.handleUpdateTag(tag, lookupProvider)
        this.loadAdditional(tag, lookupProvider)
    }


    override fun getUpdateTag(registries: HolderLookup.Provider): CompoundTag {
        val tag = super.getUpdateTag(registries)
        this.saveAdditional(tag, registries)
        return tag
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        writeToNbt(tag, registries)
        super.saveAdditional(tag, registries)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        readFromNbt(tag, registries)
        super.loadAdditional(tag, registries)
    }
}
