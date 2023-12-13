package com.rjd.taskmananger.repository;

import com.rjd.taskmananger.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Integer> {

}
