package com.codewithlei.vendora.product.exception;

public class ProductVariantNotFoundException extends RuntimeException {
    public ProductVariantNotFoundException(String message) {
        super(message);
    }
    public ProductVariantNotFoundException() {
        super("Product variant not found");
    }
}
