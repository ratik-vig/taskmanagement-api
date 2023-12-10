package com.rjd.taskmananger.dto;

public record TaskCreateResponse(
        Integer taskId,
        String taskName,
        String taskPriority,
        String taskStatus,
        String startDate,
        String endDate,
        String dueDate,
        String lastUpdated,
        String projectName,
        String assignedTo,
        String requester
) {
}
