package com.volunteer.springbootmongo.models.data;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("User")
public class User {
    @Id
    private ObjectId _id;
    private String firstname;
    private String lastname;
    private Date birth;
    private boolean gender;
    private String avatar;
    private String cover;
    @Indexed(unique = true)
    private String email;
    @Indexed(unique = true, partialFilter = "{ phonenumber : { $exists : true } }")
    private String phonenumber;

    private String address;
    private String pwd;


    public User(ObjectId _id, String firstname, String lastname, Date birth, boolean gender, String avatar, String cover, String email, String phonenumber, String address, String pwd) {
        this._id = _id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birth = birth;
        this.gender = gender;
        this.avatar = avatar;
        this.cover = cover;
        this.email = email;
        this.phonenumber = phonenumber;
        this.address = address;
        this.pwd = pwd;
    }

    public User(String firstname, String lastname, String email, String phonenumber, String pwd) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.pwd = pwd;
    }

    public User() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
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
    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
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

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
