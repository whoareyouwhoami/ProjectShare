package com.project.share.service;

import com.project.share.dao.MessageProjectDao;
import com.project.share.dao.MessageProjectDetailDao;
import com.project.share.dao.MessageProjectUserDao;
import com.project.share.model.*;
import com.project.share.model.keys.MessageProjectUserKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageProjectServiceImpl implements MessageProjectService {
    @Autowired
    private MessageProjectDao messageProjectDao;

    @Autowired
    private MessageProjectUserDao messageProjectUserDao;

    @Autowired
    private MessageProjectDetailDao messageProjectDetailDao;

    @Override
    public MessageProject createMessageProject(Project project) {
        MessageProject messageProject = new MessageProject();
        messageProject.setProject(project);
        messageProject.setName("Message - " + project.getTitle());
        return messageProjectDao.save(messageProject);

    }

    @Override
    public MessageProject getMessageProject(Project project) {
        return messageProjectDao.findByProject(project);
    }

    @Override
    public void addMessageProjectUser(User user, MessageProject messageProject) {
        MessageProjectUserKey key = new MessageProjectUserKey();
        key.setMessageProjectId(messageProject.getId());
        key.setUserId(user.getId());

        MessageProjectUser messageProjectUser = new MessageProjectUser();
        messageProjectUser.setId(key);
        messageProjectUser.setUser(user);
        messageProjectUser.setMessageProject(messageProject);
        messageProjectUserDao.save(messageProjectUser);
    }

    @Override
    public void saveMessageProjectDetail(User sender, MessageProject messageProject, String content) {
        MessageProjectDetail detail = new MessageProjectDetail();
        detail.setSender(sender);
        detail.setMessageProject(messageProject);
        detail.setContent(content);
        messageProjectDetailDao.save(detail);
    }

    @Override
    public boolean checkMessageProjectUserExist(User user, MessageProject messageProject) {
        MessageProjectUser result = messageProjectUserDao.findByUserAndMessageProject(user, messageProject);
        return result != null;
    }

}
