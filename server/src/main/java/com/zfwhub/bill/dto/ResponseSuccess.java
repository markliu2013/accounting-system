package com.zfwhub.bill.dto;

public class ResponseSuccess<T> extends BaseResponse {

    private T data;
    
    public ResponseSuccess() {
        super(true);
    }
    
    public ResponseSuccess(T data) {
        super(true);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
