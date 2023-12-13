package com.rjd.taskmananger.repository;

import com.rjd.taskmananger.model.Comments;
import com.rjd.taskmananger.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comments, Integer> {

    Optional<List<Comments>> findCommentsByTaskId(Task task);
}
