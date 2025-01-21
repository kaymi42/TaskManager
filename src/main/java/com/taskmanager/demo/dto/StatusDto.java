package com.taskmanager.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class StatusDto {
    @NotNull
    @Pattern(regexp = "WAITING|IN_PROCESS|COMPLETED")
    private String status;
}
