package com.wks.packet;

import io.netty.buffer.ByteBuf;

/**
 * 规定出 packet 解析 这个行为
 * 泛型 表示使用的 packet 类型
 */
public interface PacketAnalysis<T> {
    ByteBuf encode();

    /**
     * 当出现更多类型协议的时候 ，这里就
     *
     * @param buf
     * @return
     */
    T decode(ByteBuf buf);

    byte getCommandOperation();

    byte getSerializerAlgorithm();

}
