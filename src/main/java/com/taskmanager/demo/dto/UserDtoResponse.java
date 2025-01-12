package com.taskmanager.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDtoResponse {
    private Long id;
    private String username;
    private String email;
}
