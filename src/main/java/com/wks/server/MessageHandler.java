package com.wks.server;

import com.wks.packet.Command;
import com.wks.packet.Packet;
import com.wks.packet.data.MessageRequestData;
import com.wks.packet.data.MessageResponseData;
import com.wks.serializer.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageHandler extends SimpleChannelInboundHandler<MessageRequestData> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestData msg) throws Exception {
        //反序列化数据
        System.out.println("客户端消息："+msg);

        //反馈
        MessageResponseData messageResponseData=new MessageResponseData("服务端回复【" + msg.getMessage() + "】");
        ctx.channel().writeAndFlush(messageResponseData);


    }

}
