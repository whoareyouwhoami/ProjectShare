package com.project.share.service;

import com.project.share.dao.ProjectDao;
// import com.project.share.es.ProjectSearchDao;
import com.project.share.exception.ProjectException;
import com.project.share.model.Project;
import com.project.share.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    public Project getProject(int pid) {
        return projectDao
                .findById(pid)
                .orElse(null);
    }

    @Override
    public List<Project> getAllProject() {
        return projectDao.findAll();
    }

    @Override
    public List<Project> getAllUserProject(User user) {
        return projectDao.findAllByUser(user);
    }

    @Override
    public Project saveProject(Project project) {
        return projectDao.save(project);
    }

    @Override
    public Project updateProject(Project prev, Project current) {
        if(current.getDateStart().isBefore(prev.getDateStart())) {
            return null;
        }
        prev.setTitle(current.getTitle());
        prev.setDescription(current.getDescription());
        prev.setMember(current.getMember());
        prev.setDateStart(current.getDateStart());
        prev.setDateEnd(current.getDateEnd());
        return projectDao.save(prev);
    }

    @Override
    public void removeProject(int pid) {
        try {
            projectDao.deleteById(pid);
        } catch (Exception e) {
        }
    }
}
