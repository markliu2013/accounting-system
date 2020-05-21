package com.zfwhub.bill.exception;

public class ValidationViolationException extends RuntimeException {

    private static final long serialVersionUID = -5007207010553303957L;
    
    public ValidationViolationException(String msg) {
        super(msg);
    }
    
}
