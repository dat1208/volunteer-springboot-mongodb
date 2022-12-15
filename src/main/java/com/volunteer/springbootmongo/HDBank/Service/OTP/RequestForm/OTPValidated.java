package com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm;

import com.volunteer.springbootmongo.models.data.HDBankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.security.DenyAll;

/**
 * @author "KhaPhan" on 15-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTPValidated {
    private OTPResponse otpResponse;
    private HDBankAccount hdBankAccount;
}
