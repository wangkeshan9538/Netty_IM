package com.wks.packet;

import com.wks.packet.data.LoginPacketData;
import com.wks.serializer.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;

public class FirstPacket implements PacketAnalysis {

    @Override
    public ByteBuf encode() {
        return null;
    }

    @Override
    public FirstPacket decode(ByteBuf buf) {
        return this;
    }

    @Override
    public byte getCommandOperation() {
        return 0;
    }

    @Override
    public byte getSerializerAlgorithm() {
        return 0;
    }
}

