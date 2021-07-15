package com.project.share.service;

import com.project.share.model.ProjectES;

import java.util.List;

public interface ESService {
    List<ProjectES> getSearchQuery(String query);

    void saveProjectToES(ProjectES project);

    void removeProject(int projectId);
}
