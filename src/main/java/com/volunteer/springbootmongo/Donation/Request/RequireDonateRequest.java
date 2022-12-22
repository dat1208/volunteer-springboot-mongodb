package com.volunteer.springbootmongo.Donation.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "KhaPhan" on 16-Dec-22
 * @project volunteer-springboot-mongodb
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequireDonateRequest {
    private String clientID;
    private String organizationID;
    private String postID;
    private String amountDonate;
    private String bankAccUsername;
    private String fromBankAccNo;
    private String toBankAccNo;
    private String desc;
    private String wishMessage;
}
