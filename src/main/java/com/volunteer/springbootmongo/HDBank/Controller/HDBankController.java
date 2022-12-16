package com.volunteer.springbootmongo.HDBank.Controller;

import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.*;
import com.volunteer.springbootmongo.HDBank.Service.HDBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author "KhaPhan" on 12-Dec-22
 * @project volunteer-springboot-mongodb
 */
@RestController
@RequestMapping("/api/HDBank")
public class HDBankController {
    @Autowired
    private HDBankService hdBankService;

    @PostMapping("/link")
    public ResponseEntity<?> link(@RequestBody AppsLoginRequestData AppsLoginRequestData) {
        return hdBankService.LinkHDBankAccount(AppsLoginRequestData);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody AppsOTPVerifyRequestData appsOtpVerifyRequestData) {
        return hdBankService.verifyOTP(appsOtpVerifyRequestData);
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOTP(@RequestBody AppsResendOTPRequestData appsResendOTPRequestData) {
        return hdBankService.resendOTP(appsResendOTPRequestData);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AppsRegisterRequestData appsRegisterRequestData) {
        return hdBankService.registerHDBankAccount(appsRegisterRequestData);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody AppsChangePasswordRequestData appsChangePasswordRequestData) {
        return hdBankService.changePasswordHDBankAccount(appsChangePasswordRequestData);
    }

    @PostMapping("/get-balance")
    public ResponseEntity<?> getBalance(@RequestBody AppsGetBalanceRequest appsGetBalanceRequest) {
        return hdBankService.getBalance(appsGetBalanceRequest);
    }


}
