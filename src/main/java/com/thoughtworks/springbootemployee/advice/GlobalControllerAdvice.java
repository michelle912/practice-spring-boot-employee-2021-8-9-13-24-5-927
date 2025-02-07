package com.thoughtworks.springbootemployee.advice;


import com.thoughtworks.springbootemployee.exception.NoCompanyFoundException;
import com.thoughtworks.springbootemployee.exception.NoEmployeeFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GlobalControllerAdvice {

    @ExceptionHandler({NoCompanyFoundException.class, NoEmployeeFoundException.class})
    public ErrorResponse handleNotFound(Exception exception) {
        return new ErrorResponse(404, "Entity Not Found.");
    }
}
