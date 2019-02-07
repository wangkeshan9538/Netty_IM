package com.wks.protocolTest;

import com.wks.packet.Command;
import com.wks.packet.Packet;
import com.wks.packet.PacketAnalysis;
import com.wks.packet.data.LoginPacketData;
import com.wks.serializer.Serializer;
import com.wks.serializer.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

import static com.wks.packet.Packet.*;

public class TestProtocol {

    public static void main(String[] args) {

        // packet 到buffer
        LoginPacketData data = new LoginPacketData(1, "wks", "wks");
        Packet p = new Packet(Command.LOGIN_REQUEST, SerializerAlgorithm.DEFAULT.serialize(data));
        ByteBuf buf = p.encode();

        // buffer 到 packet 再到 指令和对象
        Packet p2 = new Packet();
        p2.decode(buf);
        Class c = Command.getDataType(p2.getCommandOperation());
        Object o = SerializerAlgorithm.getSerializer(p2.getSerializerAlgorithm()).deserialize(c, p2.data);
        System.out.println(o);

        PacketAnalysis p3 = new Packet();
    }
}