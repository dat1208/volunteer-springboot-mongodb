package com.volunteer.springbootmongo.models.data;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Account_User")
public class Account_User {
    private ObjectId _id;
    private String Account_id;
    private boolean is_admin;

    public Account_User(String account_id, boolean is_admin) {
        Account_id = account_id;
        this.is_admin = is_admin;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getAccount_id() {
        return Account_id;
    }

    public void setAccount_id(String account_id) {
        Account_id = account_id;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }
}
