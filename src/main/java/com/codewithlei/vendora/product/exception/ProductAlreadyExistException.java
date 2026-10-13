package com.codewithlei.vendora.product.exception;

public class ProductAlreadyExistException extends RuntimeException {
    public ProductAlreadyExistException(String message) {
        super(message);
    }
    public ProductAlreadyExistException() {
        super("Seller product already exist");
    }
}
