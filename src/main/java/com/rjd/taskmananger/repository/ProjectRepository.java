package com.rjd.taskmananger.repository;

import com.rjd.taskmananger.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<Project> findByProjectId(Integer projectId);
}
