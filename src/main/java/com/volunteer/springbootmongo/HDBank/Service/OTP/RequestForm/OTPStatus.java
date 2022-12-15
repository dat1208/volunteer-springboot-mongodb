package com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
public enum OTPStatus {
    DELIVERED, FAILED, SUCCESS, TIMEOUT, WRONG_FORMAT, LIMIT_RESEND_TIME
}
