package com.taskmanager.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentContentDto {
    @NotNull
    @Size(min = 10, max = 100)
    @Pattern(regexp = "[\\w\\s\\p{Punct}]{10,100}")
    private String content;
}
