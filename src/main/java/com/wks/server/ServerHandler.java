package com.wks.server;

import com.wks.Utils;
import com.wks.packet.Command;
import com.wks.packet.Packet;
import com.wks.packet.data.LoginRequestData;
import com.wks.packet.data.LoginResponseData;
import com.wks.packet.data.MessageRequestData;
import com.wks.packet.data.MessageResponseData;
import com.wks.serializer.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import static com.wks.Utils.LOGIN_SUCCESS;

@Deprecated
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        //解码
        ByteBuf requestByteBuf = (ByteBuf) msg;
        Packet p2 = new Packet();
        p2.decode(requestByteBuf);

        if (p2.getCommandOperation() == Command.LOGIN_REQUEST) {
            //反序列化数据
            LoginRequestData o = SerializerAlgorithm.getSerializer(p2.getSerializerAlgorithm()).deserialize(LoginRequestData.class, p2.data);
            System.out.println(o);
            // 验证
            if (valid(o)) {
                //标记登录
                //Utils.markAsLogin(ctx.channel());
                //返回
                LoginResponseData response = new LoginResponseData(true, LOGIN_SUCCESS);
                Packet p = new Packet(Command.LOGIN_REQUEST, SerializerAlgorithm.DEFAULT.serialize(response));
                ByteBuf buf = p.encode(ctx.alloc().buffer());
                ctx.channel().writeAndFlush(buf);
            }

        } else if(p2.getCommandOperation() == Command.MESSAGE_REQUEST){
            //反序列化数据
            MessageRequestData o = SerializerAlgorithm.getSerializer(p2.getSerializerAlgorithm()).deserialize(MessageRequestData.class, p2.data);
            System.out.println("客户端消息："+o);

            //反馈
            MessageResponseData messageResponseData=new MessageResponseData("服务端回复【" + o.getMessage() + "】");
            Packet p = new Packet(Command.MESSAGE_REQUEST, SerializerAlgorithm.DEFAULT.serialize(messageResponseData));
            ByteBuf buf = p.encode(ctx.alloc().buffer());
            ctx.channel().writeAndFlush(buf);
        }
    }

    private boolean valid(LoginRequestData o) {
        System.out.println("登录信息：" + o);
        return true;
    }
}
