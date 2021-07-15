package com.project.share.model.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MessageProjectUserKey implements Serializable {
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "message_project_id")
    private Integer messageProjectId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMessageProjectId() {
        return messageProjectId;
    }

    public void setMessageProjectId(Integer messageProjectId) {
        this.messageProjectId = messageProjectId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((messageProjectId == null) ? 0 : messageProjectId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MessageProjectUserKey other = (MessageProjectUserKey) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (messageProjectId == null) {
            if (other.messageProjectId != null)
                return false;
        } else if (!messageProjectId.equals(other.messageProjectId))
            return false;
        return true;
    }
}
