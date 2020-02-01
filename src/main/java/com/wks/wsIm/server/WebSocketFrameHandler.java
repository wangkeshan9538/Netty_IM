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

import com.wks.wsIm.biz.*;
import com.wks.wsIm.domain.Packet;
import com.wks.wsIm.domain.resp.ErrorResp;
import com.wks.wsIm.domain.resp.UserResp;
import com.wks.wsIm.serializer.JSONSerializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.wks.wsIm.biz.LoginService.SESSION;
import static com.wks.wsIm.biz.WriteService.send;
import static com.wks.wsIm.domain.Commands.ERROR;
import static com.wks.wsIm.domain.Commands.GET_USER_LIST;

/**
 * 处理 WebSocketFrame ，其中控制帧已经在上一个Handler 处理了，所以只需要处理Text 和binary
 * 所做的事情： 1.反序列化为Packet,2.拼接分帧的消息
 */
@Slf4j
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    List<TextWebSocketFrame> messages = new ArrayList<>();

    Router router = new Router();

    public static JSONSerializer serializer = new JSONSerializer();


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
                req = serializer.deserialize(msg);
                result = router.router(generateContext(ctx.channel(),req.getTraceId()), req);
                if (result != null) {
                    send(ctx.channel(),result);
                }
            }
        } catch (Exception e) {
            log.error("handler 错误", e);
            send(ctx.channel(),new Packet(ERROR,req.getTraceId(), new ErrorResp("handle错误")));
        }
    }

    //如果信息被分帧，需要拼接
    public String joinMsg(List<TextWebSocketFrame> messages) {
        StringBuilder builder = new StringBuilder();
        messages.stream().forEach((c) -> {
            builder.append(c.text());
        });
        return builder.toString();
    }

    MsgContext generateContext(Channel channel,String traceId) {
        MsgContext context = new MsgContext();
        context.setChannel(channel);
        context.setTraceId(traceId);
        return context;
    }

    //下线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        OffLineService.process(ctx);
    }
}
