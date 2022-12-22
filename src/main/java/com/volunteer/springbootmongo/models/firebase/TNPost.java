package com.volunteer.springbootmongo.models.firebase;

import org.bson.types.ObjectId;

import java.util.List;

public class TNPost {
    private List<JoinPostModel> joinPostModel;

    public TNPost(List<JoinPostModel> joinPostModel) {
        this.joinPostModel = joinPostModel;
    }

    public TNPost() {

    }

    public List<JoinPostModel> getJoinPostModel() {
        return joinPostModel.stream().toList();
    }



    public void setJoinPostModel(List<JoinPostModel> joinPostModel) {
        this.joinPostModel = joinPostModel;
    }

}
