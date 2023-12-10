package com.rjd.taskmananger.dto;

import java.util.Set;

public record UserDto (
        Integer userId,
        String userFname,
        String userLname,
        String userEmail,
        Set<ProjectCreateResponse> projects
){
}
