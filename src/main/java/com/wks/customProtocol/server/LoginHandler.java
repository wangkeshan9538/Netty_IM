package com.wks.customProtocol.server;

import com.wks.customProtocol.Utils;
import com.wks.customProtocol.packet.Command;
import com.wks.customProtocol.packet.Packet;
import com.wks.customProtocol.packet.data.LoginRequestData;
import com.wks.customProtocol.packet.data.LoginResponseData;
import com.wks.customProtocol.serializer.SerializerAlgorithm;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.wks.customProtocol.Utils.LOGIN_SUCCESS;

public class LoginHandler extends SimpleChannelInboundHandler<LoginRequestData> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestData msg) throws Exception {
        // 验证
        if (valid(msg)) {
            LoginResponseData response = new LoginResponseData(true, LOGIN_SUCCESS);
            ctx.channel().writeAndFlush(response);

            //绑定session
            Utils.bindSession(new Session(msg.getUserId(), msg.getUsername()), ctx.channel());
        }
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Utils.unBindSession(ctx.channel());
    }


    private boolean valid(LoginRequestData o) {
        System.out.println("登录信息：" + o);
        return true;
    }
}
