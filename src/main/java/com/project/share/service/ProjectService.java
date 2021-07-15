package com.project.share.service;

import com.project.share.model.Project;

import java.util.List;

public interface ProjectService {
    Project getProject(int pid);

    Project saveProject(Project project);

    Project updateProject(Project projectPrev, Project projectNew);

    List<Project> getAllProject();

    void removeProject(int pid);

}
