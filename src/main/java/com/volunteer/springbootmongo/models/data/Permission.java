package com.volunteer.springbootmongo.models.data;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document("Permission")
public class Permission {

    private ObjectId _id;
    private String account_user_id;
    private String user_id;
    private String permission_type_id;

    public Permission(String account_user_id, String user_id, String permission_type_id) {
        this.account_user_id = account_user_id;
        this.user_id = user_id;
        this.permission_type_id = permission_type_id;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getAccount_user_id() {
        return account_user_id;
    }

    public void setAccount_user_id(String account_user_id) {
        this.account_user_id = account_user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPermission_type_id() {
        return permission_type_id;
    }

    public void setPermission_type_id(String permission_type_id) {
        this.permission_type_id = permission_type_id;
    }
}
