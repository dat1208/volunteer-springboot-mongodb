package com.volunteer.springbootmongo.HDBank.Service;

import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.HDBankAccountRequestData;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.HDBankRegister;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.OTPVerifyRequestData;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientResponse.Message;
import com.volunteer.springbootmongo.HDBank.Interface.HDBankUserInterface;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPMessage;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPResponse;
import com.volunteer.springbootmongo.HDBank.Service.OTP.TwilioOTPService;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.HDBankRequest.HDBankConfig;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.HDBankRequest.HDBankRequest;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.HDBankRegisterData;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Response.RegisterResponse;
import com.volunteer.springbootmongo.models.data.HDBankAccount;
import com.volunteer.springbootmongo.models.data.User;
import com.volunteer.springbootmongo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author "KhaPhan" on 12-Dec-22
 * @project volunteer-springboot-mongodb
 */
@RequiredArgsConstructor
@Service
public class HDBankService implements HDBankUserInterface {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final HDBankConfig hdBankConfig;
    @Autowired
    private final HDBankRequest hdBankRequest;
    @Autowired
    private TwilioOTPService twilioOTPService;

    private String credential(String username, String password) {
        if (hdBankConfig.getPublicKey().equals("")) {
            hdBankRequest.getHDBankPublicKey();
        }
        System.out.println("HDBANK KEY : " + hdBankConfig.getPublicKey());
        return RSAKeyPairGenerator.getRSAKeyPairGenerator(username, password, hdBankConfig.getPublicKey());
    }


    private boolean isLinkedWithAnOtherAccount(String clientID, String AccountNo) {
        Optional<User> userStored = userRepository.findById(clientID).stream().findFirst();
        List<HDBankAccount> storedHdBankAccountList = userStored.get().getHdBankAccountList().stream().toList();
        boolean isExistedLikedToBank = storedHdBankAccountList != null;
        if (isExistedLikedToBank) {
            for (int index = 0; index < storedHdBankAccountList.size(); index++) {
                System.out.println(storedHdBankAccountList.get(index).getAccountNumber());
                if (AccountNo.equals(storedHdBankAccountList.get(index).getAccountNumber())) {
                    return false;
                }
            }
        }
        return true;
    }

    public ResponseEntity<?> LinkHDBankAccount(HDBankAccountRequestData HDBankAccountRequestData) {
        String credential = credential(HDBankAccountRequestData.getUsername(), HDBankAccountRequestData.getPassword());
        String AccountNo = hdBankRequest.linkClient(credential);
        HDBankAccount hdBankAccount = new HDBankAccount();
        hdBankAccount.setAccountNumber(AccountNo);
        hdBankAccount.setUserID(HDBankAccountRequestData.getClientID());

        if (!AccountNo.isEmpty()) {
            if (isLinkedWithAnOtherAccount(HDBankAccountRequestData.getClientID(), AccountNo)) {
                Map<String, Object> failureMessage = new HashMap<>();
                failureMessage.put("accountNo", AccountNo);
                failureMessage.put("message", Message.this_HDBank_account_linked_please_choose_another_account);
                return ResponseEntity.status(HttpStatus.OK).body(failureMessage);
            }
            OTPResponse otpResponse = twilioOTPService.sendOTPPhone(HDBankAccountRequestData.getClientID(), HDBankAccountRequestData.getPhone(), hdBankAccount);
            Map<String, Object> verifyMessage = new HashMap<>();
            verifyMessage.put("otp", otpResponse);
            verifyMessage.put("verifyURL", "/api/HDBank/verify");
            return ResponseEntity.status(HttpStatus.OK).body(verifyMessage);
        }
        Map<String, Object> failureMessage = new HashMap<>();
        failureMessage.put("HDBankUser", HDBankAccountRequestData.getUsername());
        failureMessage.put("message", Message.link_HDBank_account_failure_username_or_password_were_wrong);
        return ResponseEntity.status(HttpStatus.OK).body(failureMessage);
    }

    public boolean storedHDBankAccount(String clientID, HDBankAccount hdBankAccount) {
        try {
            Optional<User> userStored = userRepository.findById(clientID).stream().findFirst();
            if (!userStored.isEmpty()) {
                List<HDBankAccount> storedHdBankAccountList = userStored.get().getHdBankAccountList();
                boolean isExistedLikedToBank = storedHdBankAccountList != null;
                if (isExistedLikedToBank) {
                    userStored.get().getHdBankAccountList().add(hdBankAccount);
                } else {
                    List<HDBankAccount> hdBankAccountList = new ArrayList<>();
                    hdBankAccountList.add(hdBankAccount);
                    userStored.get().setHdBankAccountList(hdBankAccountList);
                }
                userRepository.save(userStored.get());
            };
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }

    //TODO Need to implement;
    public ResponseEntity<?> verifyOTP(OTPVerifyRequestData otpVerifyRequestData) {
        HDBankAccount hdBankAccount = twilioOTPService.validateOTP(otpVerifyRequestData.getOTP(), otpVerifyRequestData.getClientID());
        if (hdBankAccount != null) {
            boolean isStoredHDBankAccount = storedHDBankAccount(otpVerifyRequestData.getClientID(), hdBankAccount);
            if (isStoredHDBankAccount) {
                twilioOTPService.removeOTP(otpVerifyRequestData.getClientID());
                Map<String, Object> verifyMessage = new HashMap<>();
                verifyMessage.put("request", otpVerifyRequestData);
                verifyMessage.put("message", OTPMessage.validate_OTP_success);
                return ResponseEntity.status(HttpStatus.CREATED).body(verifyMessage);
            }
        }
        Map<String, Object> failureMessage = new HashMap<>();
        failureMessage.put("message", OTPMessage.validate_OTP_failure);
        return ResponseEntity.status(HttpStatus.OK).body(failureMessage);
    }

    @Override
    public ResponseEntity<?> registerHDBankAccount(HDBankRegister hdBankRegister) {
        HDBankRegisterData hdBankRegisterData = new HDBankRegisterData(
                credential(hdBankRegister.getUsername(), hdBankRegister.getPassword()),
                hdBankConfig.getPublicKey(),
                hdBankRegister.getEmail(),
                hdBankRegister.getFullName(),
                hdBankRegister.getPhone(),
                hdBankRegister.getIdentityNumber()
        );
        // Converto HDBANK ACCOUNT
        HDBankAccount hdBankAccount = new HDBankAccount();
        // Guiẻ request to hBDNAK -response;
        ResponseEntity<RegisterResponse> registerResponse = hdBankRequest.register(hdBankRegisterData);
        if (registerResponse.getStatusCode() == HttpStatus.OK) {
            // StoreUser
            //  OTP
            OTPResponse otpResponse = twilioOTPService.sendOTPPhone(hdBankRegister.getClientID(), hdBankRegister.getPhone(), hdBankAccount);
            Map<String, Object> verifyMessage = new HashMap<>();
            verifyMessage.put("otpMessage", otpResponse);
            verifyMessage.put("verifyURL", "/api/HDBank/verify");
            return ResponseEntity.status(HttpStatus.OK).body(verifyMessage);
        }
        return ResponseEntity.status(HttpStatus.OK).body(registerResponse.getBody());
    }
}