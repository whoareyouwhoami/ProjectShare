package com.project.share.service;

import com.project.share.es.ESProjectDao;
import com.project.share.model.Project;
import com.project.share.model.ProjectES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ESServiceImpl implements ESService {
    @Autowired
    private ESProjectDao esProjectDao;

    @Override
    public List<ProjectES> getSearchQuery(String query) {
        return esProjectDao.findByTitleOrDescription(query, query);
    }

    @Override
    public void saveProjectToES(ProjectES project) {
        esProjectDao.save(project);
    }

    @Override
    public void removeProject(int projectId) {
        esProjectDao.deleteById(projectId);
    }
}
