package com.wks.client;

import com.wks.Utils;
import com.wks.packet.Command;
import com.wks.packet.Packet;
import com.wks.packet.data.LoginPacketData;
import com.wks.packet.data.LoginResponseData;
import com.wks.packet.data.MessageResponseData;
import com.wks.serializer.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LoginPacketData data = new LoginPacketData(1, "wks", "wks");
        Packet p = new Packet(Command.LOGIN_REQUEST, SerializerAlgorithm.DEFAULT.serialize(data));
        ByteBuf buf = p.encode(ctx.alloc().buffer());
        ctx.channel().writeAndFlush(buf);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException {
        ByteBuf byteBuf = (ByteBuf) msg;

        //解码
        Packet p2 = new Packet();
        p2.decode(byteBuf);

        if (p2.getCommandOperation() == Command.LOGIN_REQUEST) {
            //反序列化数据
            LoginResponseData o = SerializerAlgorithm.getSerializer(p2.getSerializerAlgorithm()).deserialize(LoginResponseData.class, p2.data);
            // 验证
            if (o.getIsSuccessful()) {
                Utils.markAsLogin(ctx.channel());
                System.out.println("登录成功");
            } else {
                System.out.println("登录失败" + "错误代码：" + o.getCode());
            }
        } else if (p2.getCommand() == Command.MESSAGE_REQUEST) {
            MessageResponseData o = SerializerAlgorithm.getSerializer(p2.getSerializerAlgorithm()).deserialize(MessageResponseData.class, p2.data);
            System.out.println("服务器返回内容：" + o);

        }
    }
}
