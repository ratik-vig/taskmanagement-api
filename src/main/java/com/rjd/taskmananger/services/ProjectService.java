package com.rjd.taskmananger.services;

import com.rjd.taskmananger.dto.ProjectCreateRequest;
import com.rjd.taskmananger.dto.ProjectCreateResponse;
import com.rjd.taskmananger.dto.UserDto;
import com.rjd.taskmananger.model.Project;
import com.rjd.taskmananger.model.User;
import com.rjd.taskmananger.repository.ProjectRepository;
import com.rjd.taskmananger.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;


    public ProjectCreateResponse createProject(ProjectCreateRequest project) throws EntityNotFoundException {

        User user = userRepository.findById(project.userId()).orElseThrow(EntityNotFoundException::new);
        Project newProject = null;

        try {
            newProject = projectRepository.save(new Project(null, project.projectName(), new Date(project.startDate()), null, new HashSet<User>()));
            user.getProjects().add(newProject);
            newProject.getUser().add(user);

        }catch(Exception e){
            throw new RuntimeException("Error creating project");
        }

        return new ProjectCreateResponse(
                newProject.getProjectId(),
                newProject.getProjectName(),
                newProject.getStartDate(),
                newProject.getCompletionDate()
        );
    }

    public List<ProjectCreateResponse> getAllProjects() {

        try {
            List<Project> projects = projectRepository.findAll();
            List<ProjectCreateResponse> projectDtos = (List<ProjectCreateResponse>) projects
                    .stream()
                    .map(project ->
                        new ProjectCreateResponse(
                        project.getProjectId(),
                        project.getProjectName(),
                        project.getStartDate(),
                        project.getCompletionDate()
                    )


            ).collect(Collectors.toList());
            return projectDtos;
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error getting project");
        }
    }
}
