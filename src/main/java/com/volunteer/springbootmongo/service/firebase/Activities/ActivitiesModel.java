package com.volunteer.springbootmongo.service.firebase.Activities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author "KhaPhan" on 21-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivitiesModel {
    private String ActivitiesID;
    private String organization;
    private String organizationAvt;
    private String name;
    private String startDate;
    private String location;
    private int numOfVolunteer;
    private boolean isDisable;
    private String QR;
}
