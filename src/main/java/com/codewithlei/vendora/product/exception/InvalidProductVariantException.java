package com.codewithlei.vendora.product.exception;

public class InvalidProductVariantException extends RuntimeException {
    public InvalidProductVariantException(String message) {
        super(message);
    }
    public InvalidProductVariantException() {
        super("Product variant cannot be below zero");
    }

}
