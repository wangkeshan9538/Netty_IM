package com.wks.wsIm.domain;

import lombok.Data;

/**
 * 描述一个完整的指令及数据
 */
@Data
public class Packet {

    private String command;

    /**
     * 不为空则说明是应答模式
     */
    private String traceId;

    private Object data;

    public Packet(String command, String traceId, Object data) {
        this.command = command;
        this.traceId = traceId;
        this.data = data;
    }

    public Packet(String traceId, Object data) {
        this.traceId = traceId;
        this.data = data;
    }
}
