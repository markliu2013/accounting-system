package com.zfwhub.bill.dto;

public class BaseResponse {
    
    private boolean success;

    public BaseResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
