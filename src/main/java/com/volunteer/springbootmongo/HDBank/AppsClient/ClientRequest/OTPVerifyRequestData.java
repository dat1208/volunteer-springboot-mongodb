package com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest;

import com.volunteer.springbootmongo.models.data.HDBankAccount;
import lombok.*;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTPVerifyRequestData {
    private String clientID;
    private String OTP;
}
