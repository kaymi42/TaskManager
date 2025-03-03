package com.taskmanager.demo.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskDto {

    @NotNull
    @Size(min = 10, max = 100)
    @Pattern(regexp = "[\\w\\s\\p{Punct}]{10,100}")
    private String title;
    @NotNull
    @Pattern(regexp = "WAITING|IN_PROCESS|COMPLETED")
    private String status;
    @NotNull
    @Pattern(regexp = "HIGH|NORMAL|LOW")
    private String priority;
}
