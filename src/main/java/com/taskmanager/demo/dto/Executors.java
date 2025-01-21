package com.taskmanager.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class Executors {
    @NotNull
    private List<String> executorsNames;
}
