package com.wks.serializer;


public interface SerializerAlgorithm {
    /**
     * json 序列化标识
     */
    byte JSON = 1;


    Serializer DEFAULT = new JSONSerializer();

    //根据给的选项获得序列化工具实例
    static Serializer getSerializer(byte a) {
        switch (a) {
            case SerializerAlgorithm.JSON:
                return SerializerAlgorithm.DEFAULT;
        }
        return null;
    }
}
