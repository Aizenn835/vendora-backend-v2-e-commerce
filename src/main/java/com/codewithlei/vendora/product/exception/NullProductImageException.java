package com.codewithlei.vendora.product.exception;

public class NullProductImageException extends RuntimeException {
    public NullProductImageException(String message) {
        super(message);
    }
    public NullProductImageException() {
        super("Product image cannot be null or empty.");
    }
}
