package com.volunteer.springbootmongo.models;

import com.volunteer.springbootmongo.models.data.Account;
import com.volunteer.springbootmongo.models.data.Account_User;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("User")
public class RegisterForm {
    private ObjectId _id;

    private String firstname;

    private String lastname;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true, partialFilter = "{ phonenumber : { $exists : true } }")
    private String phonenumber;

    private String password;

    private String type;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
