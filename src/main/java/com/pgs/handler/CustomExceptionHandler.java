package com.pgs.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by mmalek on 2/14/2017.
 */
@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Access Denied")
    @ExceptionHandler(AccessDeniedException.class)
    public void accessDeniedExceptionHandler(){
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    ErrorInfo handleException(HttpServletRequest request, Exception ex)  {
        return new ErrorInfo(request.getRequestURL().toString(), ex);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({RuntimeException.class, Exception.class})
    public void handleGenericExceptions()  {
    }
}
