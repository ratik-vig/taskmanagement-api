package com.rjd.taskmananger.dto;

public record CommentResponse(
        String comment,
        String commentDate,
        String user
) {
}
