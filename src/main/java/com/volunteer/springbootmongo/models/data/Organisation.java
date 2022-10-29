package com.volunteer.springbootmongo.models.data;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document("Organisation")
public class Organisation {
    private ObjectId _id;
    private String name;
    private String address;

    public Organisation(ObjectId _id, String name, String address) {
        this._id = _id;
        this.name = name;
        this.address = address;
    }

    public Organisation(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
