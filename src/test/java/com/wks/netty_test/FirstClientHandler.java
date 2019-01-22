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

        //标记 read指针 中间可以通过writerIndex 指定 read 指针
        buffer.markReaderIndex();
        buffer.resetReaderIndex();

        //读写API
        buffer.writeBytes(new byte[2]);
        buffer.readBytes(new byte[2]);

        //get set 方法不能修改read write 的指针
        buffer.getByte(1);
        buffer.setByte(1,1);

        //由于 Netty 使用了堆外内存，而堆外内存是不被 jvm 直接管理的，也就是说申请到的内存无法被垃圾回收器直接回收，所以需要我们手动回收。有点类似于c语言里面，申请到的内存必须手工释放，否则会造成内存泄漏。
        //Netty 的 ByteBuf 是通过引用计数的方式管理的，如果一个 ByteBuf 没有地方被引用到，需要回收底层内存。默认情况下，当创建完一个 ByteBuf，它的引用为1，然后每次调用 retain() 方法， 它的引用就加一， release() 方法原理是将引用计数减一，减完之后如果发现引用计数为0，则直接回收 ByteBuf 底层的内存。
        buffer.retain();
        buffer.release();

        // slice 和 duplicate 和buffer 使用同一个引用，但是指针不同，等于多了一套指针组合
        //slice() 只截取从 readerIndex 到 writerIndex 之间的数据，它返回的 ByteBuf 的最大容量被限制到 原始 ByteBuf 的 readableBytes(), 而 duplicate() 是把整个 ByteBuf 都与原始的 ByteBuf 共享
        // copy() 会直接从原始的 ByteBuf 中拷贝所有的信息，包括读写指针以及底层对应的数据，因此，往 copy() 返回的 ByteBuf 中写数据不会影响到原始的 ByteBuf
        //slice() 和 duplicate() 不会改变 ByteBuf 的引用计数，所以原始的 ByteBuf 调用 release() 之后发现引用计数为零，就开始释放内存，调用这两个方法返回的 ByteBuf 也会被释放，这个时候如果再对它们进行读写，就会报错。因此，我们可以通过调用一次 retain() 方法 来增加引用，表示它们对应的底层的内存多了一次引用，引用计数为2，在释放内存的时候，需要调用两次 release() 方法，将引用计数降到零，才会释放内存
        buffer.slice();
        buffer.duplicate();
        buffer.copy();

        //slice 或duplicate的同时增加引用计数
        buffer.retainedSlice();
        buffer.retainedDuplicate();


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