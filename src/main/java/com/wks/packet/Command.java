package com.wks.packet;

import com.wks.packet.data.LoginPacketData;

public interface Command {
    byte LOGIN_REQUEST = 1;

    static Class getDataType(byte type) {
        switch (type) {
            case 1:
                return LoginPacketData.class;
        }
        return null;
    }
}