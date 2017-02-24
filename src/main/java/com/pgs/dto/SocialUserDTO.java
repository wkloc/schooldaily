package com.pgs.dto;

import com.pgs.enums.ESocialType;

/**
 * Created by mmalek on 2/22/2017.
 */
public class SocialUserDTO {

    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String image;
    private ESocialType socialType;

    public SocialUserDTO() {
    }

    public SocialUserDTO(String id, String email, String firstname, String lastname, String image, ESocialType socialType) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.image = image;
        this.socialType = socialType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ESocialType getSocialType() {
        return socialType;
    }

    public void setSocialType(ESocialType socialType) {
        this.socialType = socialType;
    }
}
