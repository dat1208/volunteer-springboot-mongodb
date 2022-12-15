package com.volunteer.springbootmongo.HDBank.Service.OTP;

import com.volunteer.springbootmongo.models.data.HDBankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ValidateOTPData {
    private String OTP;
    private Object data;
    private Date expiredTime = new Date(System.currentTimeMillis() + 300_000); // 5 minutes
    private int resendTime = 5;
    public ValidateOTPData(String OTP, Object data) {
        this.OTP = OTP;
        this.data = data;
    }
}

