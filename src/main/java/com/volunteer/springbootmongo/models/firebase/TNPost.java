package com.volunteer.springbootmongo.models.firebase;

import org.bson.types.ObjectId;

import java.util.List;

public class TNPost {
    private List<String> listUsers;

    public List<String> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<String> listUsers) {
        this.listUsers = listUsers;
    }

    public TNPost(List<String> listUsers) {
        this.listUsers = listUsers;
    }
    public TNPost() {
    }
}
