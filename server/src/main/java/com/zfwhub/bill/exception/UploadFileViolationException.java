package com.zfwhub.bill.exception;

public class UploadFileViolationException extends RuntimeException {

    private static final long serialVersionUID = 4963164500843414015L;

    public UploadFileViolationException(String msg) {
        super(msg);
    }
    
}
