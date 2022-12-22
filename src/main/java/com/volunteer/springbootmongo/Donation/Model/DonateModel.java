package com.volunteer.springbootmongo.Donation.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author "KhaPhan" on 15-Dec-22
 * @project volunteer-springboot-mongodb
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Document("DonateModel")
public class DonateModel {
    private String _id;
    private String postID;
    private String organizationID;
    private List<DonateHistory> donateHistoryList = new ArrayList<>();
    private Date initTime = new Date();
    private Date endTime;
    private int numberOfDonate = donateHistoryList.size();
    private boolean isActive = true;
    private String shortDesc;
    private String longDesc;

    public DonateModel(String postID, String organizationID, Date initTime, Date endTime, boolean isActive, String shortDesc, String longDesc) {
        this.postID = postID;
        this.organizationID = organizationID;
        this.initTime = initTime;
        this.endTime = endTime;
        this.isActive = isActive;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
    }
}
