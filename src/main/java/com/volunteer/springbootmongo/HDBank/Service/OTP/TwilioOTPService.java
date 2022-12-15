package com.volunteer.springbootmongo.HDBank.Service.OTP;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.volunteer.springbootmongo.HDBank.Service.HDBankService;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPMessage;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPResponse;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPStatus;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPValidated;
import com.volunteer.springbootmongo.models.data.HDBankAccount;
import com.volunteer.springbootmongo.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
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
    private static final String REGION_PHONE_NUMBER_VIE = "+84";
    Map<String, ValidateOTPData> OTPMap = new HashMap<>();

    //   Send OTP and Refresh OTP There;
    private String getPhoneFormat(String phone) {
        return REGION_PHONE_NUMBER_VIE + phone.substring(1);
    }
    public OTPResponse resendOTPPhone(String clientID, String phoneNumber) {
        try {
            int resendTime = OTPMap.get(clientID).getResendTime();
            if(resendTime > 0) {
                OTPMap.get(clientID).setOTP(twilioSendOTP(phoneNumber));
                OTPMap.get(clientID).setResendTime(resendTime - 1);
                OTPMap.get(clientID).setExpiredTime(new Date(System.currentTimeMillis() + 300_000));
            }
            else {
                return new OTPResponse(OTPStatus.LIMIT_RESEND_TIME, OTPMessage.current_request_is_limit_resend_OTP, 0);
            }
        } catch (Exception ex) {
            return new OTPResponse(OTPStatus.FAILED, OTPMessage.send_OTP_failure_please_resend, 0);
        }
        return new OTPResponse(OTPStatus.DELIVERED, OTPMessage.send_OTP_successfully_waiting_for_validate, 300);
    }
    public String twilioSendOTP(String phoneNumber) {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
        String OTP = generateOTP();
        String MessageBody = "Việc Tử Tế: " + OTP + " - là mã xác minh của bạn!";
        try {
            Message message = Message.creator(
                            new PhoneNumber(getPhoneFormat(phoneNumber)),
                            new PhoneNumber(twilioConfig.getTrialNumber()),
                            MessageBody)
                    .create();
        } catch (Exception ex) {
            LOGGER.error("Exception Twilio: {}", ex);
            throw ex;
        }
        return OTP;
    }

    public OTPResponse sendOTPPhone(String clientID, String phoneNumber, Object data) {
        try {
            String OTP = twilioSendOTP(phoneNumber);
            ValidateOTPData validateOTPData = new ValidateOTPData(OTP, data);
            OTPMap.put(clientID, validateOTPData);
        } catch (Exception ex) {
            return new OTPResponse(OTPStatus.FAILED, OTPMessage.send_OTP_failure_please_resend, 0);
        }
        return new OTPResponse(OTPStatus.DELIVERED, OTPMessage.send_OTP_successfully_waiting_for_validate, 300);
    }
    private boolean isTimeoutOTP(Date expiredTime) {
        Date currentDate = new Date(System.currentTimeMillis());
        return expiredTime.compareTo(currentDate) == -1;
    }
    private boolean isOTPFormat(String OTP) {
        if (OTP.matches("\\d+") && OTP.length() == 6)
            return true;
        return false;
    }
    public OTPValidated validateOTP(String OTPFromClient, String clientID) {
        OTPValidated otpValidated = new OTPValidated();

        if (!isOTPFormat(OTPFromClient)) {
            otpValidated.setOtpResponse(new OTPResponse(
                    OTPStatus.WRONG_FORMAT,
                    OTPMessage.the_OTP_is_wrong_format,
                    300));
            return otpValidated;
        }
        boolean isExistedOTP = OTPMap.get(clientID) != null;
        if (isExistedOTP) {

            boolean isTimeoutOTP = isTimeoutOTP(OTPMap.get(clientID).getExpiredTime());
            if (isTimeoutOTP) {
                otpValidated.setOtpResponse(new OTPResponse(
                        OTPStatus.TIMEOUT,
                        OTPMessage.the_OTP_timeout_please_resend_new_OTP,
                        0));
                return otpValidated;
            }

            boolean isValidOTP = OTPFromClient.equals(OTPMap.get(clientID).getOTP());
            if (!isValidOTP) {
                otpValidated.setOtpResponse(new OTPResponse(
                        OTPStatus.FAILED,
                        OTPMessage.OTP_not_match_try_again,
                        300));
                return otpValidated;
            }

            otpValidated.setOtpResponse(new OTPResponse(
                    OTPStatus.SUCCESS,
                    OTPMessage.validate_OTP_success,
                    0));
            otpValidated.setHdBankAccount((HDBankAccount) OTPMap.get(clientID).getData());
            LOGGER.info("[OTP Data{}]", OTPMap.get(clientID).getData());
            return otpValidated;
        }

        LOGGER.info("[Validate OTP Failure]");
        otpValidated.setOtpResponse(new OTPResponse(
                OTPStatus.TIMEOUT,
                OTPMessage.the_OTP_timeout_please_resend_new_OTP,
                0));
        return otpValidated;
    }
    public void removeOTP(String clientID) {
        LOGGER.info("[Remove OTP {}]", clientID);
        OTPMap.remove(clientID);
    }
    @Scheduled(fixedDelay = 300000)
    public void scheduleRemoveOvertimeOTP() {
        LOGGER.info("[Remove Overtime OTP Task]");
        if (!OTPMap.isEmpty()) {
            for (Map.Entry<String, ValidateOTPData> entry : OTPMap.entrySet()) {
                if (isTimeoutOTP(OTPMap.get(entry.getKey()).getExpiredTime())) {
                    LOGGER.info("Entry OTP Key: {}, Data: {}", entry.getKey(), OTPMap.get(entry.getKey()).getData());
                    OTPMap.remove(entry.getKey());
                }
            }
        }
    }
    private static String generateOTP() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}
