package com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest;

import lombok.*;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppsOTPVerifyRequestData {
    private String clientID;
    private String OTP;
}
