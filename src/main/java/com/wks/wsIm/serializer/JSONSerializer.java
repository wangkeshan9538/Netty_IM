package com.wks.wsIm.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wks.wsIm.domain.Commands;
import com.wks.wsIm.domain.Packet;

import java.util.Enumeration;


public class JSONSerializer implements Serializer<Packet,String>{


    @Override
    public String serialize(Packet packet) {
        return JSONObject.toJSONString(packet,false);
    }

    @Override
    public Packet deserialize(String s) {
        return JSONObject.parseObject(s,Packet.class);
    }

    @Override
    public Object desData(Packet packet, Class<?> reqtype) {
        if(reqtype.equals( Void.TYPE)){
            return null;
        }

        JSONObject o=(JSONObject)packet.getData();
        return o.toJavaObject(reqtype);
    }

    @Override
    public String serData(Object data) {
        return null;
    }


}
