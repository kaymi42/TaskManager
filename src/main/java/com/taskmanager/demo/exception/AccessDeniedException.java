package com.taskmanager.demo.exception;

import com.taskmanager.demo.dto.AppError;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException{

    private AppError apiResponse;
    private String action;
    private String resourceName;

    public AccessDeniedException(String action, String resourceName) {
        super();
        this.action = action;
        this.resourceName = resourceName;
        this.setApiResponse();
    }

    private void setApiResponse() {
        String message = String.format("You don't have permission to %s the %s", action, resourceName);
        this.apiResponse = new AppError(HttpStatus.NOT_FOUND.value(), message);
    }
}
