package com.codewithlei.vendora.common.globalExceptionHandler;

import com.codewithlei.vendora.product.exception.NullProductImageException;
import com.codewithlei.vendora.product.exception.ProductAlreadyExistException;
import com.codewithlei.vendora.product.exception.ProductNotFoundException;
import com.codewithlei.vendora.product.exception.ProductVariantNotFoundException;
import com.codewithlei.vendora.seller.exception.InvalidSellerStatusException;
import com.codewithlei.vendora.seller.exception.SellerAlreadyExistException;
import com.codewithlei.vendora.seller.exception.SellerProfileNotFound;
import com.codewithlei.vendora.users.exception.ProfileImageNullException;
import com.codewithlei.vendora.users.exception.UserAlreadyExistException;
import com.codewithlei.vendora.users.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException e){
        ErrorResponse error = ErrorResponse.builder()
                .status(404)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(ProfileImageNullException.class)
    public ResponseEntity<?> handleNullOrEmptyProfileImg(ProfileImageNullException e){
        ErrorResponse error = ErrorResponse.builder()
                .status(400)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExist(UserAlreadyExistException e){
        ErrorResponse error = ErrorResponse.builder()
                .status(409)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }

    // seller exception
    @ExceptionHandler(SellerProfileNotFound.class)
    public ResponseEntity<?> handleSellerNotFound(SellerProfileNotFound e){
        ErrorResponse error = ErrorResponse.builder()
                .status(404)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(InvalidSellerStatusException.class)
    public ResponseEntity<?> handleInvalidSellerStatus(InvalidSellerStatusException e){
        ErrorResponse error = ErrorResponse.builder()
                .status(400)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(SellerAlreadyExistException.class)
    public ResponseEntity<?> handleSellerAlreadyExist(SellerAlreadyExistException e){
        ErrorResponse error = ErrorResponse.builder()
                .status(409)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }

    // Product exception
    @ExceptionHandler(NullProductImageException.class)
    public ResponseEntity<?> handleInvalidProductImg(NullProductImageException e){
        ErrorResponse error = ErrorResponse.builder()
                .status(400)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFound(ProductNotFoundException e){
        ErrorResponse error = ErrorResponse.builder()
                .status(404)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity<?> handleProductAlreadyExist(ProductAlreadyExistException e){
        ErrorResponse error = ErrorResponse.builder()
                .status(409)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(ProductVariantNotFoundException.class)
    public ResponseEntity<?> handleProductVariantNotFound(ProductVariantNotFoundException e){
        ErrorResponse error = ErrorResponse.builder()
                .status(404)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

}
