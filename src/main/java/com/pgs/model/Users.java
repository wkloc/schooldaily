package com.pgs.model;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by mmalek on 2/14/2017.
 */
@Entity
public class Users {

    @Id
    @Column(updatable = false, nullable = false)
    @Size(min = 0, max = 50)
    private String username;

    @Size(min = 0, max = 500)
    private String password;

    @Column(name = "email", length = 80)
    private String email;

    @Column
    @Size(min = 0, max = 50)
    private String firstname;

    @Column
    @Size(min = 0, max = 50)
    private String lastname;

    @Column(name = "facebook_id", length = 500)
    private String facebookId;

    @Column(name = "facebook_image", length = 500)
    private String facebookImage;

    @Column(name = "github_id", length = 500)
    private String githubId;

    @Column(name = "github_image", length = 500)
    private String githubImage;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "authority"))
    private Set<Authority> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public String getFacebookImage() {
        return facebookImage;
    }

    public void setFacebookImage(String facebookImage) {
        this.facebookImage = facebookImage;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public String getGithubImage() {
        return githubImage;
    }

    public void setGithubImage(String githubImage) {
        this.githubImage = githubImage;
    }
}
