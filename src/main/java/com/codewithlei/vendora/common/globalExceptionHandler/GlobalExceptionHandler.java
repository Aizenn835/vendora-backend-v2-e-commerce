package com.codewithlei.vendora.common.globalExceptionHandler;

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
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }
    @ExceptionHandler(ProfileImageNullException.class)
    public ResponseEntity<?> handleNullOrEmptyProfileImg(ProfileImageNullException e){
        ErrorResponse error = ErrorResponse.builder()
                .status(400)
                .message(e.getMessage())
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleUserAlreadyExist(UserAlreadyExistException e){
        ErrorResponse error = ErrorResponse.builder()
                .status(409)
                .message(e.getMessage())
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(error);
    }
}
