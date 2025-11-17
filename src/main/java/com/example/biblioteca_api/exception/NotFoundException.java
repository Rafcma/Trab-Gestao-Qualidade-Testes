package com.example.biblioteca_api.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) { super(msg); }
}