package com.project.share.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class MessageChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name; // Chat room name

    /*
     * true - message is created but haven't sent any messages
     * false - conversation started
     */
    @Column(columnDefinition="BIT(1) DEFAULT 0")
    private boolean emptyMessage;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime created;

    @OneToMany(mappedBy = "messageChat")
    private Set<MessageDetail> messageDetailSet;

    public MessageChat() {}

    public MessageChat(Project project, User user, String name) {
        this.project = project;
        this.user = user;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmptyMessage() {
        return emptyMessage;
    }

    public void setEmptyMessage(boolean emptyMessage) {
        this.emptyMessage = emptyMessage;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Set<MessageDetail> getMessageDetailSet() {
        return messageDetailSet;
    }

    public void setMessageDetailSet(Set<MessageDetail> messageDetailSet) {
        this.messageDetailSet = messageDetailSet;
    }
}
