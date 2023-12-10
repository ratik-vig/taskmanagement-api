package com.rjd.taskmananger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rjd.taskmananger.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name="projects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="project_id")
    private Integer projectId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "completion_date")
    private Date completionDate;

    @ManyToMany(mappedBy = "projects")
    @JsonIgnore
    private Set<User> user;
}
