package com.taskmanager.demo.service.impl;

import com.taskmanager.demo.dto.*;
import com.taskmanager.demo.entity.User;
import com.taskmanager.demo.exception.BadRequestException;
import com.taskmanager.demo.exception.UnauthorizedException;
import com.taskmanager.demo.utils.JwtTokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
public class AuthServiceImpl {
    private final UserServiceImpl userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest request){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e ){
            AppError apiResponse = new AppError(HttpStatus.BAD_REQUEST.value(),"Incorrect login or password");
            throw new BadRequestException(apiResponse);
        }
        UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto){
        if(!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())){
            AppError apiResponse = new AppError(HttpStatus.BAD_REQUEST.value(),"Passwords do not match");
            throw new BadRequestException(apiResponse);
        }
        if(userService.existsByUsername(registrationUserDto.getUsername())){
            AppError apiResponse = new AppError(HttpStatus.UNAUTHORIZED.value(),
                    String.format("User with '%s' username already exist", registrationUserDto.getUsername()));
            throw new UnauthorizedException(apiResponse);
        }
        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDtoResponse(user.getId(), user.getUsername(), user.getEmail()));
    }
}
