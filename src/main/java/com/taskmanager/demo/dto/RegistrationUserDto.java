package com.taskmanager.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationUserDto {
    @NotNull
    @Size(min = 8, max = 20)
    @Pattern(regexp = "\\w{8,20}")
    private String username;
    @NotNull
    @Size(min = 8, max = 20)
    @Pattern(regexp = "[\\w\\p{Punct}]{8,20}")
    private String password;
    @NotNull
    private String confirmPassword;
    @Email
    private String email;
}
