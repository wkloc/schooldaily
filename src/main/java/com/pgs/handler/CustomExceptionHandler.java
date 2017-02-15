package com.pgs.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by mmalek on 2/14/2017.
 */
@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Access Denied")
    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedExceptionHandler(){
    }
}
