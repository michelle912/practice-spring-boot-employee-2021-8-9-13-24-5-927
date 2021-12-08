package com.thoughtworks.springbootemployee.exception;

public class NoCompanyFoundException extends Exception {
    public NoCompanyFoundException(String message) {
        super(message);
    }
}
