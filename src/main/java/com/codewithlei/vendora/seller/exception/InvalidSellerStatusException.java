package com.codewithlei.vendora.seller.exception;

public class InvalidSellerStatusException extends RuntimeException {
    public InvalidSellerStatusException(String message) {
        super(message);
    }
    public InvalidSellerStatusException() {
        super("Only pending applications can be approved");
    }
}
