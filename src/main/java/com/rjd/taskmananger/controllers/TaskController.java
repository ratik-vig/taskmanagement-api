package com.rjd.taskmananger.controllers;

import com.rjd.taskmananger.dto.*;
import com.rjd.taskmananger.services.TaskService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<Map> createTask(@RequestBody TaskCreateRequest task){
        Map<String, Object> response = new HashMap<>();
        try{
            TaskCreateResponse resp = taskService.createNewTask(task);
            response.put("data", resp);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(EntityNotFoundException e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getByProjectId/{projectId}")
    public ResponseEntity<Map> getTasksByProjectId(@PathVariable Integer projectId){
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", taskService.getTasksByProjectId(projectId));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(EntityNotFoundException e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch(Exception e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Map> getTasksById(@PathVariable Integer taskId){
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("data", taskService.getTaskById(taskId));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(EntityNotFoundException e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch(Exception e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/getByProjectId/{projectId}")
//    public ResponseEntity<Map> getTasksByProjectId(@PathVariable Integer projectId){
//        Map<String, Object> response = new HashMap<>();
//        try {
//            response.put("data", taskService.getTasksByProjectId(projectId));
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }catch(EntityNotFoundException e){
//            response.put("error", e.getMessage());
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }catch(Exception e){
//            response.put("error", e.getMessage());
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<Map> updatedTask(@PathVariable Integer taskId, @RequestBody TaskCreateRequest request){
        Map<String, Object> response = new HashMap<>();
        try{
            TaskCreateResponse resp = taskService.updateTaskById(taskId, request);
            response.put("data", resp);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(EntityNotFoundException e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch(Exception e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addComment/{taskId}")
    public ResponseEntity<Map> addComment(@PathVariable Integer taskId, @RequestBody CommentRequest request){
        Map<String, Object> response = new HashMap<>();
        try{
            CommentResponse resp = taskService.addComment(taskId, request);
            response.put("data", resp);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(EntityNotFoundException e){
            response.put("error", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch(Exception e){
            response.put("error", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
