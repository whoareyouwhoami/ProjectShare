package com.project.share.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.project.share.validate.ValidateProjectDateDifference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@ValidateProjectDateDifference(
        fieldStart = "dateStart",
        fieldEnd = "dateEnd"
)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Enter title")
    private String title;

    @NotEmpty(message = "Enter description")
    @Column(columnDefinition = "TEXT")
    // @Lob -> longtext
    private String description;

    private int member;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateEnd;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateUpload;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateModified;

    @Column(columnDefinition="BIT(1) DEFAULT 0")
    private boolean status;

    /* AUTHOR OF THE PROJECT */
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    /* TO CHECK MEMBERS IN THE PROJECT GROUP */
    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<ProjectUser> projectUserSet;

    /* CHECK ANY PRIVATE MESSAGE RECEIVED FOR THE PROJECT */
    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private Set<MessageChat> messageChatSet;

    /* THIS MAY NOT BE NECESSARY SINCE UNIDIRECTIONAL */
    @JsonIgnore
    @OneToOne(mappedBy = "project")
    private MessageProject messageProjectSet;

    public Project() {}

    public Project(String title, String description, int member, LocalDate dateStart, LocalDate dateEnd) {
        this.title = title;
        this.description = description;
        this.member = member;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public LocalDateTime getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(LocalDateTime dateUpload) {
        this.dateUpload = dateUpload;
    }

    public LocalDateTime getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<ProjectUser> getProjectUserSet() {
        return projectUserSet;
    }

    public void setProjectUserSet(Set<ProjectUser> projectUserSet) {
        this.projectUserSet = projectUserSet;
    }

    public Set<MessageChat> getMessageChatSet() {
        return messageChatSet;
    }

    public void setMessageChatSet(Set<MessageChat> messageChatSet) {
        this.messageChatSet = messageChatSet;
    }

    public MessageProject getMessageProjectSet() {
        return messageProjectSet;
    }

    public void setMessageProjectSet(MessageProject messageProjectSet) {
        this.messageProjectSet = messageProjectSet;
    }
}
