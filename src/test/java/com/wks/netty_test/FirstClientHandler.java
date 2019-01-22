package com.wks.netty_test;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端写出数据");

        // 1. 获取数据
        ByteBuf buffer = getByteBuf(ctx);

        // 2. 写数据
        ctx.channel().writeAndFlush(buffer);


        buffer.capacity();

        //如果发现容量不足，则进行扩容，直到扩容到 maxCapacity，超过这个数，就抛异常
        buffer.maxCapacity();

        //writerIndex - readerIndex  当前刻度的字节数
        buffer.readableBytes();

        //当前可写的字节数,单如果容量达到capacity ,那么就拓展,直至到达maxCapacity
        buffer.writableBytes();

        //获得和设置读指针
        buffer.readerIndex();
        buffer.readerIndex(1);

        //获得和设置写指针
        buffer.writerIndex();
        buffer.writerIndex(1);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        // 1. 获取二进制抽象 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();

        // 2. 准备数据，指定字符串的字符集为 utf-8
        byte[] bytes = "你好，闪电侠!".getBytes(Charset.forName("utf-8"));

        // 3. 填充数据到 ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }
}

/*
*
*
*
*/