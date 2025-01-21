package com.taskmanager.demo.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ExceptionHandlerList {
    private Integer status;
    private List<String> message;
    private Date timestamp;

    public ExceptionHandlerList(Integer status, List<String> message){
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
