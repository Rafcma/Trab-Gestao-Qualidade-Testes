package com.example.biblioteca_api.exception;

public class DataIntegrityException extends RuntimeException {
    public DataIntegrityException(String msg) {
        super(msg);
    }
}
