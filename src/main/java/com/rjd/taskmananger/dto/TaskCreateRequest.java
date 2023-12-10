package com.rjd.taskmananger.dto;

import com.rjd.taskmananger.model.Task;

public record TaskCreateRequest (
    String taskName,

    Task.Priority taskPriority,

    Task.Status taskStatus,

    String startDate,

    String endDate,
    String dueDate,
    String lastUpdated,
    Integer projectId,
    Integer assignedTo,
    Integer assignedBy
){

}
