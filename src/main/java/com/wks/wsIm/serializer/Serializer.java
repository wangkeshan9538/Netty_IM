package com.wks.wsIm.serializer;

import com.wks.wsIm.domain.Packet;

/**
 * 序列化和反序列化的抽象类
 *
 * @param <T> 用于内部处理的，实体对象
 * @param <I> 序列化后的对象
 */
public interface Serializer<T, I> {
    I serialize(T t);

    T deserialize(I i);

    Object desData(T packet, Class<?> reqtype);

    I serData(Object data);

}
