package com.taskmanager.demo.exception;

import com.taskmanager.demo.dto.AppError;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    private AppError apiResponse;

    public UnauthorizedException(AppError apiResponse) {
        super();
        this.apiResponse = apiResponse;
    }

}
