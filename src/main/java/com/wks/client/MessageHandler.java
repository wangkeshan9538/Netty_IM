package com.wks.client;

import com.wks.packet.data.MessageResponseData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MessageHandler extends SimpleChannelInboundHandler<MessageResponseData>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponseData msg) throws Exception {
        System.out.println("服务器返回内容：" + msg);
    }
}
