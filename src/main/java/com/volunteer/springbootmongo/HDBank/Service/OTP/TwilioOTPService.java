package com.volunteer.springbootmongo.HDBank.Service.OTP;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.volunteer.springbootmongo.HDBank.Service.HDBankService;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPMessage;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPResponse;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPStatus;
import com.volunteer.springbootmongo.models.data.HDBankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Service
public class TwilioOTPService {
    @Autowired
    TwilioConfig twilioConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioOTPService.class);
    Map<String, ValidateOTPData> OTPMap = new HashMap<>();

    public OTPResponse sendOTPPhone(String clientID, String phoneNumber, HDBankAccount data) {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());

        PhoneNumber phoneNumberFrom = new PhoneNumber(twilioConfig.getTrialNumber());
        PhoneNumber phoneNumberTo = new PhoneNumber(phoneNumber);

        String OTP = generateOTP();
        String MessageBody = "Việc Tử Tế: " + OTP + " - là mã xác minh của bạn!";
        try {
            Message message = Message.creator(
                            phoneNumberTo,
                            phoneNumberFrom,
                            MessageBody)
                    .create();
            ValidateOTPData validateOTPData = new ValidateOTPData(OTP, data);
            OTPMap.put(clientID, validateOTPData);

        } catch (Exception ex) {
            return new OTPResponse(OTPStatus.FAILED, OTPMessage.send_OTP_failure_please_resend, 0);
        }
        return new OTPResponse(OTPStatus.DELIVERED, OTPMessage.send_OTP_successfully_waiting_for_validate, 300);
    }

    public HDBankAccount validateOTP(String OTPFromClient, String clientID) {
        if (OTPFromClient.equals(OTPMap.get(clientID).getOTP())) {
            System.out.println(OTPMap.get(clientID).getData());
            return OTPMap.get(clientID).getData();
        }
        return null;
    }
    public void removeOTP(String clientID) {
        OTPMap.remove(clientID);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        //TODO: Implementation need to do
        return true;
    }

    private static String generateOTP() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }

}
