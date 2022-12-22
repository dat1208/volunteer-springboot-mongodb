package com.volunteer.springbootmongo.service.firebase.Activities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "KhaPhan" on 21-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QRModel {
    private String userID;
    private String activitiesID;
}
