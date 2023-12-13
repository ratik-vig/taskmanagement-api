package com.rjd.taskmananger.services;

import com.rjd.taskmananger.dto.*;
import com.rjd.taskmananger.model.Comments;
import com.rjd.taskmananger.model.Project;
import com.rjd.taskmananger.model.Task;
import com.rjd.taskmananger.model.User;
import com.rjd.taskmananger.repository.CommentRepository;
import com.rjd.taskmananger.repository.ProjectRepository;
import com.rjd.taskmananger.repository.TaskRepository;
import com.rjd.taskmananger.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CommentRepository commentRepository;

    public TaskCreateResponse createNewTask(TaskCreateRequest request) throws EntityNotFoundException{
        User assignedTo = userRepository.findByUserId(request.assignedTo())
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("User id not found");
                });
        User assignedBy = userRepository.findByUserId(request.assignedBy())
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("User id not found");
                });

        Project project = projectRepository.findByProjectId(request.projectId())
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Project not found with ID");
                });

        Task newTask = null;

        try{
            newTask = taskRepository.save(
                    new Task(
                            null,
                            request.taskName(),
                            request.taskPriority(),
                            request.taskStatus(),
                            new Date(request.startDate()),
                            new Date(request.dueDate()),
                            request.endDate() != "" ? new Date(request.endDate()) : null,
                            new Date(request.lastUpdated()),
                            project,
                            assignedTo,
                            assignedBy
                    )
            );
        }catch(Exception e){
            throw new RuntimeException("Error creating task");
        }
        return new TaskCreateResponse(
                newTask.getTaskId(),
                newTask.getTaskName(),
                newTask.getTaskPriority().toString(),
                newTask.getTaskStatus().toString(),
                newTask.getStartDate().toString(),
                newTask.getEndDate() == null ? "" : newTask.getEndDate().toString(),
                newTask.getDueDate().toString(),
                newTask.getLastUpdated().toString(),
                newTask.getProject().getProjectName(),
                newTask.getAssignedTo().getUserFname() + " " + newTask.getAssignedTo().getUserLname(),
                newTask.getAssignedBy().getUserFname() + " " + newTask.getAssignedBy().getUserLname()
        );
    }

    public TaskCreateResponse getTaskById(Integer taskId) throws EntityNotFoundException{
        Task newTask = taskRepository.findByTaskId(taskId).orElseThrow(() -> {
            throw new EntityNotFoundException("Task not found with ID: " + taskId);
        });

        return new TaskCreateResponse(
                newTask.getTaskId(),
                newTask.getTaskName(),
                newTask.getTaskPriority().toString(),
                newTask.getTaskStatus().toString(),
                newTask.getStartDate().toString(),
                newTask.getEndDate() == null ? "" : newTask.getEndDate().toString(),
                newTask.getDueDate().toString(),
                newTask.getLastUpdated().toString(),
                newTask.getProject().getProjectName(),
                newTask.getAssignedTo().getUserFname() + " " + newTask.getAssignedTo().getUserLname(),
                newTask.getAssignedBy().getUserFname() + " " + newTask.getAssignedBy().getUserLname()
        );
    }

    public List<TaskCreateResponse> getTasksByProjectId(Integer projectId) throws EntityNotFoundException{
        Project project = projectRepository.findByProjectId(projectId).orElseThrow(() -> {
            throw new EntityNotFoundException("Project not found with ID: " + projectId);
        });

        try{
            List<TaskCreateResponse> tasks = taskRepository.findByProject(project)
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(newTask -> new TaskCreateResponse(
                            newTask.getTaskId(),
                            newTask.getTaskName(),
                            newTask.getTaskPriority().toString(),
                            newTask.getTaskStatus().toString(),
                            newTask.getStartDate().toString(),
                            newTask.getEndDate() == null ? "" : newTask.getEndDate().toString(),
                            newTask.getDueDate().toString(),
                            newTask.getLastUpdated().toString(),
                            newTask.getProject().getProjectName(),
                            newTask.getAssignedTo().getUserFname() + " " +newTask.getAssignedTo().getUserLname(),
                            newTask.getAssignedBy().getUserFname() + " " +newTask.getAssignedBy().getUserLname()
                    )).collect(Collectors.toList());
            return tasks;
        }catch(Exception e){
            throw new RuntimeException("Error creating task");
        }
    }
    public TaskCreateResponse updateTaskById(Integer taskId, TaskCreateRequest request) throws EntityNotFoundException{

        Task task = taskRepository.findByTaskId(taskId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Task not found with ID: " + taskId);
                });
        User assignedTo = userRepository.findByUserId(request.assignedTo())
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("User id not found");
                });
        User assignedBy = userRepository.findByUserId(request.assignedBy())
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("User id not found");
                });
        Task updatedTask = null;
        try{
            task.setTaskName(request.taskName());
            task.setStartDate(new Date(request.startDate()));
            task.setDueDate(new Date(request.dueDate()));
            task.setTaskPriority(request.taskPriority());
            task.setTaskStatus(request.taskStatus());
            task.setLastUpdated(new Date());
            task.setEndDate(request.endDate() == "" ? null : new Date(request.endDate()));
            task.setAssignedBy(assignedBy);
            task.setAssignedTo(assignedTo);
            updatedTask = taskRepository.save(task);
        }catch(Exception e){
            throw new RuntimeException("Error creating task");
        }

        return new TaskCreateResponse(
                updatedTask.getTaskId(),
                updatedTask.getTaskName(),
                updatedTask.getTaskPriority().toString(),
                updatedTask.getTaskStatus().toString(),
                updatedTask.getStartDate().toString(),
                updatedTask.getEndDate() == null ? "" : updatedTask.getEndDate().toString(),
                updatedTask.getDueDate().toString(),
                updatedTask.getLastUpdated().toString(),
                updatedTask.getProject().getProjectName(),
                updatedTask.getAssignedTo().getUserFname() + " " + updatedTask.getAssignedTo().getUserLname(),
                updatedTask.getAssignedBy().getUserFname() + " " + updatedTask.getAssignedBy().getUserLname()
        );
    }

    public CommentResponse addComment(Integer taskId, CommentRequest request) throws EntityNotFoundException{
        Task task = taskRepository.findByTaskId(taskId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Task not found with ID: " + taskId);
                });
        User user = userRepository.findByUserId(request.userId())
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("User id not found");
                });
        Comments newComment = null;
        try{
            newComment = commentRepository.save(new Comments(
                    null,
                    request.comment(),
                    new Date(request.commentDate()),
                    task,
                    user
            ));
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error adding comment");
        }

        return new CommentResponse(
                newComment.getComment(),
                newComment.getCommentDate().toString(),
                newComment.getUser().getUserFname() + " " + newComment.getUser().getUserLname()
        );
    }

}
