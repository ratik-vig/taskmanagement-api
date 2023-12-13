package com.rjd.taskmananger.dto;

public record CommentRequest(
        String comment,
        String commentDate,
        Integer userId
) {
}
