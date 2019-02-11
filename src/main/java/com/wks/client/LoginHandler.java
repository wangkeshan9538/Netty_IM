package com.wks.client;

import com.wks.Utils;
import com.wks.packet.Command;
import com.wks.packet.Packet;
import com.wks.packet.data.LoginRequestData;
import com.wks.packet.data.LoginResponseData;
import com.wks.serializer.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginHandler extends SimpleChannelInboundHandler<LoginResponseData> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LoginRequestData data = new LoginRequestData(1, "wks", "wks");
        ctx.channel().writeAndFlush(data);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponseData msg) throws Exception {
        // 验证
        if (msg.getIsSuccessful()) {
            Utils.markAsLogin(ctx.channel());
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败" + "错误代码：" + msg.getCode());
        }
    }

}
