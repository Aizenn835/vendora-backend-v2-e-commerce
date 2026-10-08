package com.codewithlei.vendora.seller.exception;

public class SellerProfileNotFound extends RuntimeException {
    public SellerProfileNotFound(String message) {
        super(message);
    }
    public SellerProfileNotFound() {
        super("Seller profile not found.");
    }
}
