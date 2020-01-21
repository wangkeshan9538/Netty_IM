package com.wks.customProtocol.client;

import com.wks.customProtocol.packet.data.MessageResponseData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageHandler extends SimpleChannelInboundHandler<MessageResponseData> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponseData msg) throws Exception {
        String fromUserId = msg.getFromUserId();
        String fromUserName = msg.getFromUserName();
        System.out.println(fromUserId + ":" + fromUserName + " -> " + msg.getMessageResponse());
    }
}
