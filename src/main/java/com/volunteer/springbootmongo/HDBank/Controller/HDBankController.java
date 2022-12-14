package com.volunteer.springbootmongo.HDBank.Controller;

import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.HDBankAccountRequestData;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.HDBankRegister;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.OTPVerifyRequestData;
import com.volunteer.springbootmongo.HDBank.Service.HDBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author "KhaPhan" on 12-Dec-22
 * @project volunteer-springboot-mongodb
 */
@RestController
@RequestMapping("/api/HDBank")
public class HDBankController {
    /* POST: /api/HDBank/link {clientID, username, passwork}
    *
    * */

    @Autowired
    private HDBankService hdBankService;
    @PostMapping("/link")
    public ResponseEntity<?> link(@RequestBody HDBankAccountRequestData HDBankAccountRequestData) {
        return hdBankService.LinkHDBankAccount(HDBankAccountRequestData);
    }
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody OTPVerifyRequestData otpVerifyRequestData) {
        return hdBankService.verifyOTP(otpVerifyRequestData);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody HDBankRegister hdBankRegister) {
        return hdBankService.registerHDBankAccount(hdBankRegister);
    }


}
