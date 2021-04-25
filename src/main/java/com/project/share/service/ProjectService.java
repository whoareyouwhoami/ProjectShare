package com.project.share.service;

import com.project.share.model.Project;
import com.project.share.model.User;

import java.util.List;

public interface ProjectService {
    // Get project
    Project getProject(int pid);

    // Get all projects
    List<Project> getAllProject();

    // Get all user projects
    List<Project> getAllUserProject(User user);

    // Save project
    Project saveProject(Project project);

    // Update project
    Project updateProject(Project projectPrev, Project projectNew);

    // Remove project
    void removeProject(int pid);


    /**
     * Elasticsearch
     */
    // void esSaveProject(Project project);
}
