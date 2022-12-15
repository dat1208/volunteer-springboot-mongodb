package com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm;

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
public class OTPResponse {
    private OTPStatus status;
    private OTPMessage message;
    private int expired;
}
