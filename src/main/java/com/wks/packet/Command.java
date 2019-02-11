package com.wks.packet;

import com.wks.packet.data.LoginRequestData;
import com.wks.packet.data.LoginResponseData;
import com.wks.packet.data.MessageRequestData;
import com.wks.packet.data.MessageResponseData;

public interface Command {

    byte LOGIN_REQUEST = 1;

    byte LOGIN_RESPONSE = 2;

    byte MESSAGE_REQUEST = 3;

    byte MESSGAE_RESPONSE = 4;

    // 在handler的处理方法中，通过判断指令 来 反序列化对应的class ，并进不同的handler ，那这个方法似乎并没有必要
    // 2019-2-11 当需要根据class 分发对应的handler时则需要这个方法
    static Class getRequestDataType(byte type) {
        switch (type) {
            case 1:
                return LoginRequestData.class;
            case 2:
                return LoginResponseData.class;
            case 3:
                return MessageRequestData.class;
            case 4:
                return MessageResponseData.class;
        }
        return null;
    }
}