package com.rjd.taskmananger.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {

    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    public enum Status{
        IN_PROGRESS,
        RESOLVED,
        UNRESOLVED,
        ESCALATED
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private Integer taskId;

    @Column(name="task_name", nullable = false)
    private String taskName;

    @Column(name="task_priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority taskPriority;

    @Column(name="task_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    @Column(name = "task_start_date", nullable = false)
    private Date startDate;

    @Column(name = "task_due_date", nullable = false)
    private Date dueDate;

    @Column(name="task_end_date", nullable = true)
    private Date endDate;

    @Column(name = "task_last_updated", nullable = false)
    private Date lastUpdated;

    @ManyToOne
    @JoinColumn(name="project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name="assigned_to", nullable = false)
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "assigned_by", nullable = false)
    private User assignedBy;

}
