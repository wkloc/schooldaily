package com.pgs.dto;

/**
 * Created by mmalek on 2/22/2017.
 */
public class FacebookUserDTO {

    private String id;
    private String email;
    private String firstname;
    private String lastname;

    public FacebookUserDTO() {
    }

    public FacebookUserDTO(String id, String email, String firstname, String lastname) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "FacebookUserDTO{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
