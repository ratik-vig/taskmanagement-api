package com.rjd.taskmananger.controllers;

import com.rjd.taskmananger.dto.ProjectCreateRequest;
import com.rjd.taskmananger.dto.ProjectCreateResponse;
import com.rjd.taskmananger.model.Project;
import com.rjd.taskmananger.services.JWTService;
import com.rjd.taskmananger.services.ProjectService;
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
@RequestMapping("/api/v1/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @Autowired
    JWTService jwtService;

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<Map> createNewProject(@RequestBody ProjectCreateRequest project){
        Map<String, Object> response = new HashMap<>();
        Integer userId = 0;
        try{
            userId = jwtService.extractUserId("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyYXRpa3ZpZzIyQGdtYWlsLmNvbSIsInVzZXJJZCI6NSwiaWF0IjoxNzAyMjM5Mzc1LCJleHAiOjE3MDIzMjU3NzV9.bqfDhrfV1oKaMG2cvok-tSuW4JwHOBz57m3Ap0TvLI0");

            ProjectCreateResponse newProject = projectService.createProject(project, userId);
            response.put("data", newProject);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(EntityNotFoundException e){
            response.put("error", "User does not exist with id: " + userId);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            response.put("error", e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allProjects")
    public ResponseEntity<Map> getAllProjects(){
        Map<String, Object> response = new HashMap<>();
        try {
            List<ProjectCreateResponse> projects= projectService.getAllProjects();
            response.put("data", projects);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e){
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<Map> getProjectsByUserId(){
//
//    }
}
