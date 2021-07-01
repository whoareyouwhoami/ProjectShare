package com.project.share.service;

import com.project.share.model.MessageProject;
import com.project.share.model.Project;
import com.project.share.model.User;

public interface MessageProjectService {
    MessageProject createMessageProject(Project project);

    MessageProject getMessageProject(Project project);

    // Adding user to project group
    void addMessageProjectUser(User user, MessageProject messageProject);

    void saveMessageProjectDetail(User sender, MessageProject messageProject, String content);

    boolean checkMessageProjectUserExist(User user, MessageProject messageProject);
}
