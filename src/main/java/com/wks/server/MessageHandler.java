package com.wks.server;

import com.wks.Utils;
import com.wks.packet.Command;
import com.wks.packet.Packet;
import com.wks.packet.data.MessageRequestData;
import com.wks.packet.data.MessageResponseData;
import com.wks.serializer.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageHandler extends SimpleChannelInboundHandler<MessageRequestData> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestData msg) throws Exception {

        //拿到发送方的session
        Session session = Utils.getSession(ctx.channel());

        if (session == null) {
            return;
        }

        //构建 转发消息
        MessageResponseData messageResponseData = new MessageResponseData(session.getUserId(), session.getUserName(), msg.getMessage());
        //转发
        Channel beCalled = Utils.getChannel(msg.getToUser());
        beCalled.writeAndFlush(messageResponseData);
    }

}
