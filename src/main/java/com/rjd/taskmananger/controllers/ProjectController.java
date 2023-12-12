package com.rjd.taskmananger.controllers;

import com.rjd.taskmananger.dto.ProjectCreateRequest;
import com.rjd.taskmananger.dto.ProjectCreateResponse;
import com.rjd.taskmananger.dto.TaskCreateRequest;
import com.rjd.taskmananger.dto.TaskCreateResponse;
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
    public ResponseEntity<Map> createNewProject(@RequestBody ProjectCreateRequest project, @RequestHeader("Authorization") String token){
        Map<String, Object> response = new HashMap<>();
        Integer userId = 0;
        try{
            userId = jwtService.extractUserId(token.substring(7));

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

    @PutMapping("/update/{projectId}")
    public ResponseEntity<Map> updatedProject(@PathVariable Integer projectId, @RequestBody ProjectCreateRequest request){
        Map<String, Object> response = new HashMap<>();
        try{
            ProjectCreateResponse resp = projectService.updateProjectById(projectId, request);
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

//    @GetMapping("/{userId}")
//    public ResponseEntity<Map> getProjectsByUserId(){
//
//    }
}
