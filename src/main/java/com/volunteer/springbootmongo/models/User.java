package com.volunteer.springbootmongo.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("User")
public class User {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    @Indexed(unique = true)
    private String email;
    @Indexed(unique = true)
    private String phonenumber;
    private String pwd;

    public User(String firstname, String lastname, String email, String phonenumber, String pwd) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.pwd = pwd;
    }

    public User() {

    }
}
