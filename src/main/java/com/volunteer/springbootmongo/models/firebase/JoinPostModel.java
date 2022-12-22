package com.volunteer.springbootmongo.models.firebase;

import java.util.Date;

public class JoinPostModel {
    private Date date;

    private String username;

    public JoinPostModel(String username, Date date) {
        this.username = username;
        this.date = date;
    }
    public JoinPostModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
