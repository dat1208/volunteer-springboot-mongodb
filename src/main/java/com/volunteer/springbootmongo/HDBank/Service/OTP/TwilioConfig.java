package com.volunteer.springbootmongo.HDBank.Service.OTP;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class TwilioConfig {
    @Value("${twilio.env.account-sid}")
    private String accountSid;

    @Value("${twilio.env.auth-token}")
    private String authToken;

    @Value("${twilio.env.trial-number}")
    private String trialNumber;
}
