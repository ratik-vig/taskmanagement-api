package com.rjd.taskmananger.dto;

import java.util.Date;
import java.util.Set;

public record ProjectCreateResponse(
        Integer projectId,
        String projectName,

        Date startDate,

        Date completionDate

) {
}
