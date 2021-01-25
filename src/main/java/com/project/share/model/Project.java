package com.project.share.model;

import com.project.share.validate.ValidateProjectDateDifference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    // @Positive
    private int member;

    // @NotEmpty(message = "Enter starting date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateStart;

    // @NotEmpty(message = "Enter ending date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEnd;

    @CreationTimestamp
    private LocalDateTime dateUpload;

    @UpdateTimestamp
    private LocalDateTime dateModified;

    @OneToOne(cascade = CascadeType.ALL)
    private User owner;

    // @Column(columnDefinition="BIT(1) DEFAULT 0")
    // private boolean visibility;

    @Column(columnDefinition="BIT(1) DEFAULT 0")
    private boolean status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ProjectUser", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> user;

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    // public boolean isVisibility() {
    //     return visibility;
    // }
    //
    // public void setVisibility(boolean visibility) {
    //     this.visibility = visibility;
    // }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }
}
