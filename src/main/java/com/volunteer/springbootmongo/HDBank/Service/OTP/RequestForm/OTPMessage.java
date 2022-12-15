package com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
public enum OTPMessage {
    send_OTP_successfully_waiting_for_validate,
    send_OTP_failure_please_resend,
    validate_OTP_success,
    the_OTP_is_wrong_format,
    OTP_not_match_try_again,
    the_OTP_timeout_please_resend_new_OTP,

    current_request_is_limit_resend_OTP
}
