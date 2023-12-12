package com.rjd.taskmananger.dto;

import java.util.Date;
import java.util.Set;

public record ProjectCreateRequest(
        String projectName,

        String startDate
){
}
