package com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppsRegisterRequestData {
    private String clientID;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String identityNumber;
}
