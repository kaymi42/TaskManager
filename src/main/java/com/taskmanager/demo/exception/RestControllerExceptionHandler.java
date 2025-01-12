package com.taskmanager.demo.exception;

import com.taskmanager.demo.dto.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFound(ResourceNotFoundException exception){
        return new ResponseEntity<>(exception.getApiResponse(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDenied(AccessDeniedException exception){
        return new ResponseEntity<>(exception.getApiResponse(), HttpStatus.FORBIDDEN);
    }
}
