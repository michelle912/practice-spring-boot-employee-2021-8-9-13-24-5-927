package com.thoughtworks.springbootemployee.exception;

public class NoEmployeeFoundException extends Exception {
    public NoEmployeeFoundException(String message) {
        super(message);
    }
}
