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
public class AppsChangePasswordRequestData {
    private String clientID;
    private String hdBankUsername;
    private String oldPassword;
    private String newPassword;
}
