package com.rjd.taskmananger.controllers;

import com.rjd.taskmananger.dto.UserDto;
import com.rjd.taskmananger.dto.UserLoginRequest;
import com.rjd.taskmananger.dto.UserLoginResponse;
import com.rjd.taskmananger.exceptions.DuplicateUserException;
import com.rjd.taskmananger.model.User;
import com.rjd.taskmananger.services.AuthenticationService;
import com.rjd.taskmananger.services.AuthenticationServiceImpl;
import com.rjd.taskmananger.services.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    private AuthenticationServiceImpl authenticationService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        Map<String, Object> response = new HashMap<>();
        try{

            UserDto newUser = authenticationService.registerUser(user);
            response.put("data", newUser);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(DuplicateUserException e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>( response, HttpStatus.CONFLICT);
        }catch(RuntimeException e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map> loginUser(@RequestBody UserLoginRequest request){
        Map<String, Object> response = new HashMap<>();
        try{
            UserLoginResponse token = authenticationService.loginUser(request);
            response.put("data", token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
