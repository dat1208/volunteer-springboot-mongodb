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
    private HDBankAccount data;
    private Date expiredTime = new Date(System.currentTimeMillis() + 180_000); // 3 minutes

    public ValidateOTPData(String OTP, HDBankAccount data) {
        this.OTP = OTP;
        this.data = data;
    }
}

