package com.project.share.model.keys;

import com.project.share.model.MessageProject;
import com.project.share.model.User;

import java.io.Serializable;

public class MessageProjectUserKey implements Serializable {
    private User user;
    private MessageProject messageProject;

    public MessageProjectUserKey(User user, MessageProject messageProject) {
        this.user = user;
        this.messageProject = messageProject;
    }
}
