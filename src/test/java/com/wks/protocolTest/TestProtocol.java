package com.wks.protocolTest;

import com.wks.customProtocol.packet.Command;
import com.wks.customProtocol.packet.Packet;
import com.wks.customProtocol.packet.PacketAnalysis;
import com.wks.customProtocol.packet.data.LoginRequestData;
import com.wks.customProtocol.serializer.Serializer;
import com.wks.customProtocol.serializer.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static com.wks.customProtocol.packet.Packet.*;

public class TestProtocol {

    public static void main(String[] args) {

        // packet 到buffer
        LoginRequestData data = new LoginRequestData("1", "wks", "wks");
        Packet p = new Packet(Command.LOGIN_REQUEST, SerializerAlgorithm.DEFAULT.serialize(data));
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        ByteBuf buf = p.encode(byteBuf);

        // buffer 到 packet 再到 指令和对象
        Packet p2 = new Packet();
        p2.decode(buf);
        Class c = Command.getRequestDataType(p2.getCommandOperation());
        Object o = SerializerAlgorithm.getSerializer(p2.getSerializerAlgorithm()).deserialize(c, p2.data);
        System.out.println(o);

        PacketAnalysis p3 = new Packet();
    }
}