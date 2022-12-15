package com.volunteer.springbootmongo.models.data;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Permission_Type")
public class Permission_Type {
    private ObjectId _id;
    private String name;
    private boolean delete;
    private boolean read;
    private boolean write;

    public Permission_Type(String name, boolean delete, boolean read, boolean write) {
        this.name = name;
        this.delete = delete;
        this.read = read;
        this.write = write;
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

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }
}
