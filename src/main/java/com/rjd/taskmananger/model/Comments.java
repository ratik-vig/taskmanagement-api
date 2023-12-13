package com.rjd.taskmananger.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comment_id;

    @Column(name="comment_text", nullable = false)
    private String comment;

    @Column(name = "comment_date", nullable = false)
    private Date commentDate;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task taskId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
