package com.taskmanager.demo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AppError {
    private Integer status;
    private String message;
    private Date timestamp;

    public AppError(Integer status, String message){
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }

    public static void main(String[] args) {
        AppError a = new AppError(23, "3232");
        System.out.println(a.timestamp);
    }
}
