package com.wks.customProtocol.codec;

import com.wks.customProtocol.packet.Command;
import com.wks.customProtocol.packet.Packet;
import com.wks.customProtocol.serializer.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 将解码操作 单独做一层，
 */
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //解码成Packet对象
        Packet p2 = new Packet();
        p2.decode(in);

        //根据 序列化方式 和command 获得
        Object data = SerializerAlgorithm.getSerializer(p2.getSerializerAlgorithm())
                .deserialize(Command.getRequestDataType(p2.getCommand()), p2.data);

        out.add(data);

    }

}
