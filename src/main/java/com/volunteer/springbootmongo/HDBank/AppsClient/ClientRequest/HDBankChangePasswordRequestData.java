package com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "KhaPhan" on 14-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HDBankChangePasswordRequestData {
    private String clientID;
    private String HDBankUID;
    private String HDBankUsername;
    private String oldPassword;
    private String newPassword;
}
