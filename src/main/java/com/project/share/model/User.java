package com.project.share.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.share.validate.ValidCheckEmail;
import com.project.share.validate.ValidCheckPasswordConfirm;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@ValidCheckPasswordConfirm(
        field = "password",
        fieldMatch = "passwordConfirm"
)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Enter first name")
    private String firstName;

    @NotEmpty(message = "Enter last name")
    private String lastName;

    @NotEmpty(message = "Select gender")
    private String gender;

    @NotEmpty(message = "Enter email")
    @ValidCheckEmail
    private String email;

    @NotEmpty(message = "Enter password")
    private String password;

    @Transient
    @NotEmpty(message = "Confirm password")
    private String passwordConfirm;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @CreationTimestamp
    private LocalDateTime register;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserRole", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    /* GET LIST OF PROJECTS UPLOADED BY THE USER */
    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private Set<Project> projectSet;

    /* GET LIST OF PROJECT MESSAGES THAT THE USER IS INVOLVED */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<MessageProjectUser> messageProjectUserSet;

    /* GET LIST OF PRIVATE MESSAGES BETWEEN USER AND PROJECT AUTHOR */
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<MessageChat> messageChatSet;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String gender, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;

        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getRegister() {
        return register;
    }

    public void setRegister(LocalDateTime register) {
        this.register = register;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public Set<Project> getProjectSet() {
        return projectSet;
    }

    public void setProjectSet(Set<Project> projectSet) {
        this.projectSet = projectSet;
    }

    public Set<MessageProjectUser> getMessageProjectUserSet() {
        return messageProjectUserSet;
    }

    public void setMessageProjectUserSet(Set<MessageProjectUser> messageProjectUserSet) {
        this.messageProjectUserSet = messageProjectUserSet;
    }

    public Set<MessageChat> getMessageChatSet() {
        return messageChatSet;
    }

    public void setMessageChatSet(Set<MessageChat> messageChatSet) {
        this.messageChatSet = messageChatSet;
    }
}
