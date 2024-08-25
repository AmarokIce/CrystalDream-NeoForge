package club.someoneice.crystaldream.core.packet

import club.someoneice.crystaldream.util.createModPath
import club.someoneice.crystaldream.util.sendClientDisplayMessage
import io.netty.buffer.ByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.neoforged.neoforge.network.handling.IPayloadContext


data class PacketSendClientMessage(
    val message: String
): CustomPacketPayload {
    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> = TYPE

    companion object {
        val TYPE: CustomPacketPayload.Type<PacketSendClientMessage> = CustomPacketPayload.Type(createModPath("send_message_packet"))

        val STREAM_CODEC: StreamCodec<ByteBuf, PacketSendClientMessage> = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            PacketSendClientMessage::message,
            ::PacketSendClientMessage
        )

        fun handleData(data: PacketSendClientMessage, context: IPayloadContext) {
            context.enqueueWork {
                context.player().sendClientDisplayMessage(data.message)
            }
        }
    }
}