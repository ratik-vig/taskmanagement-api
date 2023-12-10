package com.rjd.taskmananger.repository;

import com.rjd.taskmananger.model.Project;
import com.rjd.taskmananger.model.Task;
import com.rjd.taskmananger.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    Optional<Task> findByTaskId(Integer taskId);
    Optional<List<Task>> findByProject(Project project);
}
