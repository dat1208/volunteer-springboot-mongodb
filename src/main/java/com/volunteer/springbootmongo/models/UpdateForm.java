package com.volunteer.springbootmongo.models;

import java.util.Date;

public class UpdateForm {

    private String oldmail;
    private String firstname;
    private String lastname;
    private String gender;
    private Date birth;
    private String phonenumber;
    private String email;
    private String address;

    public UpdateForm(String firstname,String lastname, String gender, Date birth, String phonenumber, String email, String address) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birth = birth;
        this.phonenumber = phonenumber;
        this.email = email;
        this.address = address;
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


    public String getOldmail() {
        return oldmail;
    }

    public void setOldmail(String oldmail) {
        this.oldmail = oldmail;
    }

    public String getGender() {
        return gender;
    }

    public String isGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
