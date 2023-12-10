package com.rjd.taskmananger.dto;

import com.rjd.taskmananger.model.Project;

import java.util.Set;

public record UserRequest(
        Integer userId,
        String userFname,
        String userLname,
        String userEmail,
        String password
){

}
