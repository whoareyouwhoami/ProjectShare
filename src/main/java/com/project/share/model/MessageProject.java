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
public class MessageProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    private String name;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime created;

    @OneToMany(mappedBy = "messageProject")
    private Set<MessageProjectDetail> messageProjectDetailSet;

    @OneToMany(mappedBy = "messageProject")
    private Set<MessageProjectUser> messageProjectUserSet;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Set<MessageProjectDetail> getMessageProjectDetailSet() {
        return messageProjectDetailSet;
    }

    public void setMessageProjectDetailSet(Set<MessageProjectDetail> messageProjectDetailSet) {
        this.messageProjectDetailSet = messageProjectDetailSet;
    }

    public Set<MessageProjectUser> getMessageProjectUserSet() {
        return messageProjectUserSet;
    }

    public void setMessageProjectUserSet(Set<MessageProjectUser> messageProjectUserSet) {
        this.messageProjectUserSet = messageProjectUserSet;
    }
}
