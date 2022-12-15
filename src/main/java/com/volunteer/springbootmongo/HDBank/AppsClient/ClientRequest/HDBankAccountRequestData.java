package com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author "KhaPhan" on 12-Dec-22
 * @project volunteer-springboot-mongodb
 */
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class HDBankAccountRequestData {
    private String clientID; // Email, Phone, ID
    private String username;
    private String password;
    private String email;
    private String phone;
}
