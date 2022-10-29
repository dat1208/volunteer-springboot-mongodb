package com.volunteer.springbootmongo.models.data;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Account")
public class Account {

    private ObjectId _id;
    private String User_id;
    private String type;
    private String organisation_id;

    public Account(String user_id, String type, String organisation_id) {
        User_id = user_id;
        this.type = type;
        this.organisation_id = organisation_id;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrganisation_id() {
        return organisation_id;
    }

    public void setOrganisation_id(String organisation_id) {
        this.organisation_id = organisation_id;
    }
}
