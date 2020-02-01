package com.wks.wsIm.domain.resp;

import lombok.Data;

@Data
public class ErrorResp {
    private String errorReason;

    public ErrorResp(String errorReason) {
        this.errorReason = errorReason;
    }
}
