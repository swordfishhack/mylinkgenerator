package com.mylink.mylinkgenerator.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TinyURLNotFoundException extends RuntimeException {

    public TinyURLNotFoundException(String exception) {
        super(exception);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
