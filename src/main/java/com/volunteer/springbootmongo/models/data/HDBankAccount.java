package com.volunteer.springbootmongo.models.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author "KhaPhan" on 12-Dec-22
 * @project volunteer-springboot-mongodb
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("HDBank_Account")
public class HDBankAccount {
    private ObjectId _id;
    private String userID;
    private String HDBankUid;
    private String HDBankUsername;
    private String HDBankPassword;
    private List<String> HDBankOldPassword = new ArrayList<>();
    private String fullName;
    private String accountNumber;
    private String phone;
    private String email;
    private String identityNumber;
    private Date dateOfBirth;
    private String address;
    private Date linkedDate = new Date();
    private Float balance;

    public HDBankAccount(String userID, String HDBankUid, String HDBankUsername, String HDBankPassword, String fullName,  String accountNumber, String phone, String email, String identityNumber) {
        this.userID = userID;
        this.HDBankUid = HDBankUid;
        this.HDBankUsername = HDBankUsername;
        this.HDBankPassword = HDBankPassword;
        this.fullName = fullName;
        this.accountNumber = accountNumber;
        this.phone = phone;
        this.email = email;
        this.identityNumber = identityNumber;
    }
}