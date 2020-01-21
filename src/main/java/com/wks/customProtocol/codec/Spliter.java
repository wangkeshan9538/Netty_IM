package com.wks.customProtocol.codec;

import com.wks.customProtocol.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 兼具  模数检查 和  拆包处理的功能
 */
public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // 屏蔽非本协议的客户端
        if (in.getInt(in.readerIndex()) != Packet.MAGIC_NUM) {
            System.out.println("关闭连接");

            ctx.channel().close();

            return null;
        }

        return super.decode(ctx, in);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //do nothing
    }
}
