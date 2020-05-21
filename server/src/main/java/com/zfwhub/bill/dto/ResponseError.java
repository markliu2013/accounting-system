package com.zfwhub.bill.dto;

public class ResponseError extends BaseResponse {
    
    private Integer errorCode;
    private String errorMsg;
    
    
    public ResponseError() {
        super(false);
    }
    
    public ResponseError(Integer errorCode) {
        super(false);
        this.errorCode = errorCode;
    }
    
    public ResponseError(Integer errorCode, String errorMsg) {
        super(false);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
    
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
}
