package com.rjd.taskmananger.controllers;

import com.rjd.taskmananger.dto.UserDto;
import com.rjd.taskmananger.dto.UserRequest;
import com.rjd.taskmananger.exceptions.DuplicateUserException;
import com.rjd.taskmananger.model.User;
import com.rjd.taskmananger.services.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        Map<String, Object> response = new HashMap<>();
        try{
            UserDto newUser = userService.registerUser(user);
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

}
