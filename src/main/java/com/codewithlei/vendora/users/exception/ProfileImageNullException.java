package com.codewithlei.vendora.users.exception;

public class ProfileImageNullException extends RuntimeException {
    public ProfileImageNullException(String message) {
        super(message);
    }

    public ProfileImageNullException() {
        super("Profile image is null");
    }
}
