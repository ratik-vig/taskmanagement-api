package com.rjd.taskmananger.dto;

public record UserLoginRequest(
        String userEmail,
        String userPassword
) {
}
