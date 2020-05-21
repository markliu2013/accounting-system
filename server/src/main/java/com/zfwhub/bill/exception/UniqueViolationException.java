package com.zfwhub.bill.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class UniqueViolationException extends DataIntegrityViolationException {

    private static final long serialVersionUID = 1424146123065978660L;

    public UniqueViolationException(String msg) {
        super(msg);
    }

}
