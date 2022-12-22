package com.volunteer.springbootmongo.Donation.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author "KhaPhan" on 16-Dec-22
 * @project volunteer-springboot-mongodb
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document("DonateHistory")
public class DonateHistory {
    private ObjectId _id;
    private Date donateTime = new Date();
    private String clientID;
    private String organizationID;
    private String amountDonate;
    private String bankAccUsername;
    private String fromBankAccNo;
    private String toBankAccNo;
    private String status;
    private String desc;

    public DonateHistory(String clientID, String organizationID, String amountDonate, String bankAccUsername, String fromBankAccNo, String toBankAccNo, String status, String desc) {
        this.clientID = clientID;
        this.organizationID = organizationID;
        this.amountDonate = amountDonate;
        this.bankAccUsername = bankAccUsername;
        this.fromBankAccNo = fromBankAccNo;
        this.toBankAccNo = toBankAccNo;
        this.status = status;
        this.desc = desc;
    }
}
