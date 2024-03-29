package com.project.share.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.project.share.model.keys.MessageProjectUserKey;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MessageProjectUser {
    @EmbeddedId
    private MessageProjectUserKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("messageProjectId")
    @JoinColumn(name = "message_project_id")
    private MessageProject messageProject;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime joined;

    public MessageProjectUser() {}
    public MessageProjectUser(MessageProjectUserKey id, User user, MessageProject messageProject) {
        this.id = id;
        this.user = user;
        this.messageProject = messageProject;
    }

    public MessageProjectUserKey getId() {
        return id;
    }

    public void setId(MessageProjectUserKey id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MessageProject getMessageProject() {
        return messageProject;
    }

    public void setMessageProject(MessageProject messageProject) {
        this.messageProject = messageProject;
    }

    public LocalDateTime getJoined() {
        return joined;
    }

    public void setJoined(LocalDateTime joined) {
        this.joined = joined;
    }
}
