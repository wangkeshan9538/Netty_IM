/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.wks.wsIm.server;

import com.wks.wsIm.biz.LoginService;
import com.wks.wsIm.biz.MsgContext;
import com.wks.wsIm.biz.Router;
import com.wks.wsIm.biz.Session;
import com.wks.wsIm.domain.Packet;
import com.wks.wsIm.serializer.JSONSerializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.wks.wsIm.biz.LoginService.SESSION;

/**
 * 处理 WebSocketFrame ，其中控制帧已经在上一个Handler 处理了，所以只需要处理Text 和binary
 * 所做的事情： 1.反序列化为Packet,2.拼接分帧的消息
 */
@Slf4j
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    List<TextWebSocketFrame> messages = new ArrayList<>();

    Router router = new Router();

    JSONSerializer serializer = new JSONSerializer();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        // ping and pong frames already handled

        Packet result = null;
        Packet req = null;
        try {
            messages.add((TextWebSocketFrame) frame);
            if (frame.isFinalFragment()) {
                String msg = joinMsg(messages);
                messages.clear();
                result = router.router(generateContext(ctx.channel()), req = serializer.deserialize(msg));
                if (result != null) {
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(serializer.serialize(result)));
                }
            }
        } catch (Exception e) {
            log.error("handler 错误", e);
            ctx.channel().writeAndFlush(new TextWebSocketFrame(serializer.serialize(new Packet(req.getTraceId(), new Error()))));

        }
    }


    public String joinMsg(List<TextWebSocketFrame> messages) {
        StringBuilder builder = new StringBuilder();
        messages.stream().forEach((c) -> {
            builder.append(c.text());
        });
        return builder.toString();
    }

    MsgContext generateContext(Channel channel) {
        MsgContext context = new MsgContext();
        context.setChannel(channel);
        return context;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        Session session = ctx.channel().attr(SESSION).get();
        if (session == null || session.getUser() == null) return;
        log.info(session.getUser().getUserName() + "退出登录");
        LoginService.getUserPool().remove(session.getUser());
    }
}