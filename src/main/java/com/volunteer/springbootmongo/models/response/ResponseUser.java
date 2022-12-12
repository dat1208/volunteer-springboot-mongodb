package com.volunteer.springbootmongo.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseUser {

    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("birth")
    private Date birth;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phonenumber")
    private String phonenumber;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("cover")
    private String cover;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("address")
    private String address;

    public ResponseUser(String firstname, String lastname, Date birth, String email, String phonenumber, String avatar, String cover, String gender, String address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birth = birth;
        this.email = email;
        this.phonenumber = phonenumber;
        this.avatar = avatar;
        this.cover = cover;
        this.gender = gender;
        this.address = address;
    }
    public ResponseUser(String firstname, String lastname, String email, String phonenumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String isGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public ResponseUser(ResponseUser responseUser) {
        this.firstname = responseUser.firstname;
        this.lastname = responseUser.lastname;
        this.birth = responseUser.birth;
        this.email = responseUser.email;
        this.phonenumber = responseUser.phonenumber;
        this.avatar = responseUser.avatar;
        this.cover = responseUser.cover;
        this.gender = responseUser.gender;
        this.address = responseUser.address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
