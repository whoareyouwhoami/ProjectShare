package com.project.share.service;

import com.project.share.dao.ProjectDao;
import com.project.share.exception.ProjectException;
import com.project.share.model.Project;
import com.project.share.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        return projectDao.findById(pid)
                .orElseThrow(
                        () -> new ProjectException("Project doesn't exist")
                );
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
    public Project updateProject(Project projectPrev, Project projectNew) {
        projectPrev.setTitle(projectNew.getTitle());
        projectPrev.setDescription(projectNew.getDescription());
        projectPrev.setMember(projectNew.getMember());

        if(projectNew.getDateStart().isBefore(projectPrev.getDateStart()))
            return null;

        projectPrev.setDateStart(projectNew.getDateStart());
        projectPrev.setDateEnd(projectNew.getDateEnd());
        return projectDao.save(projectPrev);
    }

    @Override
    public void removeProject(int pid) {
        try {
            projectDao.deleteById(pid);
        } catch (Exception e) {
        }
    }
}
