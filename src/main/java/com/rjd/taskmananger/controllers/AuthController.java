package com.rjd.taskmananger.controllers;

import com.rjd.taskmananger.model.User;
import com.rjd.taskmananger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody User user){
//        userRepository.save(user);
//        return new ResponseEntity<>("user", HttpStatus.CREATED);
//    }
}
