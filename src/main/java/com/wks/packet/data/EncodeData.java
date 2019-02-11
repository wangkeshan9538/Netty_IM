package com.wks.packet.data;

/**
 * 如果要一个统一的encode层的话 ，就需要一个统一的Data 基类 ，
 * 也再次 提供一个方法 获得Data 对应的Command
 */
public interface EncodeData {

    byte getCommand();
}
