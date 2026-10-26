package com.auca.to_do_list.service;

import com.auca.to_do_list.model.Project;
import com.auca.to_do_list.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public String saveProject(Project project) {
        projectRepository.save(project);
        return "Project saved successfully";
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(UUID id) {
        return projectRepository.findById(id);
    }

    public String updateProject(UUID id, Project newProject) {
        Optional<Project> found = projectRepository.findById(id);
        if (found.isEmpty()) {
            return "The project with that id does not exist";
        }
        Project project = found.get();
        project.setProjectName(newProject.getProjectName());
        project.setOwnerName(newProject.getOwnerName());
        project.setProjectCode(newProject.getProjectCode());
        project.setDescription(newProject.getDescription());
        projectRepository.save(project);
        return "Project updated successfully";
    }

    public String deleteProject(UUID id) {
        Optional<Project> found = projectRepository.findById(id);
        if (found.isEmpty()) {
            return "The project with that id does not exist";
        }
        projectRepository.deleteById(id);
        return "Project deleted successfully";
    }
}