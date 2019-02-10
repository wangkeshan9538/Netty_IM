package com.wks.packet;

import com.wks.packet.data.LoginPacketData;

public interface Command {

    byte LOGIN_REQUEST = 1;

    byte MESSAGE_REQUEST = 2;

    //TODO 在handler的处理方法中，通过判断指令 来进不同的handler ，那这个方法似乎并没有必要

    static Class getRequestDataType(byte type) {
        switch (type) {
            case 1:
                return LoginPacketData.class;
        }
        return null;
    }
}