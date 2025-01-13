package com.taskmanager.demo.exception;

import com.taskmanager.demo.dto.AppError;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    private AppError apiResponse;
    private String resourceName;

    public BadRequestException(String resourceName) {
        super();
        this.resourceName = resourceName;
        this.setApiResponse();
    }

    private void setApiResponse() {
        String message = String.format("%s is incorrect", resourceName);
        this.apiResponse = new AppError(HttpStatus.BAD_REQUEST.value(), message);
    }
}
