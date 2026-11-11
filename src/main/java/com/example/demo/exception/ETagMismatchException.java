package com.example.demo.exception;

public class ETagMismatchException extends RuntimeException {
    public ETagMismatchException() {
        super("The resource has been modified. Please retrieve it again and retry.");
    }
}
