package com.zfwhub.bill.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.zfwhub.bill.dto.BaseResponse;
import com.zfwhub.bill.dto.ResponseError;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResponse defaultExceptionHandler(HttpServletRequest req, Exception e) {
        System.out.println("------------------------------------------");
        System.out.println(e.getClass().getCanonicalName());
        ResponseError responseError = new ResponseError(101, e.getMessage());
        return responseError;
    }
    
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public BaseResponse exceptionHandler1(HttpServletRequest req, Exception e) {
        ResponseError responseError = new ResponseError(102, e.getMessage());
        return responseError;
    }
    
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseBody
    public BaseResponse exceptionHandler2(HttpServletRequest req, Exception e) {
        ResponseError responseError = new ResponseError(103, e.getMessage());
        return responseError;
    }
    
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseBody
    public BaseResponse exceptionHandler3(HttpServletRequest req, Exception e) {
        ResponseError responseError = new ResponseError(104, e.getMessage());
        return responseError;
    }
    
    @ExceptionHandler(value = UniqueViolationException.class)
    @ResponseBody
    public BaseResponse exceptionHandler4(HttpServletRequest req, Exception e) {
        ResponseError responseError = new ResponseError(105, e.getMessage());
        return responseError;
    }
    
    @ExceptionHandler(value = ValidationViolationException.class)
    @ResponseBody
    public BaseResponse exceptionHandler5(HttpServletRequest req, Exception e) {
        ResponseError responseError = new ResponseError(106, e.getMessage());
        return responseError;
    }
    
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public BaseResponse exceptionHandler6(HttpServletRequest req, Exception e) {
        ResponseError responseError = new ResponseError(107, "BindException");
        return responseError;
    }
    
}
