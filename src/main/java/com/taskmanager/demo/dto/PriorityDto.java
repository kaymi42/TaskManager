package com.taskmanager.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PriorityDto {
    @NotNull
    @Pattern(regexp = "HIGH|NORMAL|LOW")
    private String priority;
}
