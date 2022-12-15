package com.volunteer.springbootmongo.HDBank.Service;

import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.HDBankAccountRequestData;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.HDBankRegister;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.HDBankResendOTPRequestData;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.OTPVerifyRequestData;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientResponse.Message;
import com.volunteer.springbootmongo.HDBank.Interface.HDBankUserInterface;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPMessage;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPResponse;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPStatus;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPValidated;
import com.volunteer.springbootmongo.HDBank.Service.OTP.TwilioOTPService;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.HDBankRequest.HDBankConfig;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.HDBankRequest.HDBankRequest;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.HDBankRegisterData;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Response.RegisterResponse;
import com.volunteer.springbootmongo.models.data.HDBankAccount;
import com.volunteer.springbootmongo.models.data.User;
import com.volunteer.springbootmongo.repository.UserRepository;
import com.volunteer.springbootmongo.service.user.UserService;
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
    @Autowired
    UserService userService;

    private String credential(String username, String password) {
        if (hdBankConfig.getPublicKey().equals("")) {
            hdBankRequest.getHDBankPublicKey();
        }
        return RSAKeyPairGenerator.getRSAKeyPairGenerator(username, password, hdBankConfig.getPublicKey());
    }
    private boolean isExistLinked(String clientID, String AccountNo) {
        Optional<User> userStored = userRepository.findById(clientID).stream().findFirst();
        boolean isExistedLikedToBank = userStored.get().getHdBankAccountList() != null;
        if (isExistedLikedToBank) {
            if (userStored.get().getHdBankAccountList().size() >= 3) {
                return false;
            }
            for (int index = 0; index < userStored.get().getHdBankAccountList().size(); index++) {
                if (AccountNo.equals(userStored.get().getHdBankAccountList().get(index).getAccountNumber())) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isOverLinkedAccount(String clientID) {
        Optional<User> userStored = userRepository.findById(clientID).stream().findFirst();
        if (userStored.get().getHdBankAccountList() != null) {
            if (userStored.get().getHdBankAccountList().size() >= 3) {
                return true;
            }
        }
        return false;
    }
    public ResponseEntity<?> sendOTP(String clientID, String phone, Object data) {
        OTPResponse otpResponse = twilioOTPService.sendOTPPhone(clientID, phone, data);

        Map<String, Object> verifyMessage = new HashMap<>();
        verifyMessage.put("clientID", clientID);
        verifyMessage.put("otp", otpResponse);
        if(otpResponse.getStatus().equals(OTPStatus.SUCCESS)) {
            verifyMessage.put("verifyURL", "/api/HDBank/verify");
        }
        return ResponseEntity.status(HttpStatus.OK).body(verifyMessage);
    }
    public ResponseEntity<?> LinkHDBankAccount(HDBankAccountRequestData HDBankAccountRequestData) {
        Map<String, Object> ResponseMessage = new HashMap<>();
        if(!userService.phoneVal(HDBankAccountRequestData.getPhone()))  {
            ResponseMessage.put("message", Message.the_Phone_number_is_wrong_format);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage);
        }

        String credential = credential(HDBankAccountRequestData.getUsername(), HDBankAccountRequestData.getPassword());
        String AccountNo = hdBankRequest.linkClient(credential);

        HDBankAccount hdBankAccount = new HDBankAccount();
        hdBankAccount.setAccountNumber(AccountNo);
        hdBankAccount.setUserID(HDBankAccountRequestData.getClientID());


        if (!AccountNo.isEmpty()) {
            if (isOverLinkedAccount(HDBankAccountRequestData.getClientID())) {
                ResponseMessage.put("message", Message.current_user_is_limit_link_bank_account);
                return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage);
            }
            if (!isExistLinked(HDBankAccountRequestData.getClientID(), AccountNo)) {
                ResponseMessage.put("message", Message.this_HDBank_account_linked_please_choose_another_account);
                return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage);
            }
            return sendOTP(HDBankAccountRequestData.getClientID(), HDBankAccountRequestData.getPhone(), hdBankAccount);
        }
        ResponseMessage.put("HDBankUser", HDBankAccountRequestData.getUsername());
        ResponseMessage.put("message", Message.link_HDBank_account_failure_username_or_password_were_wrong);
        return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage);
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
            }
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }
    public ResponseEntity<?> verifyOTP(OTPVerifyRequestData otpVerifyRequestData) {

        OTPValidated otpValidated = twilioOTPService.validateOTP(otpVerifyRequestData.getOTP(), otpVerifyRequestData.getClientID());
        Map<String, Object> responseMessage = new HashMap<>();

        if (otpValidated.getHdBankAccount() != null) {
            boolean isStoredHDBankAccount = storedHDBankAccount(otpVerifyRequestData.getClientID(), otpValidated.getHdBankAccount());
            if (isStoredHDBankAccount) {
                twilioOTPService.removeOTP(otpVerifyRequestData.getClientID());
                responseMessage.put("request", otpVerifyRequestData);
                responseMessage.put("response", otpValidated.getOtpResponse());
                return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
            }
        }
        responseMessage.put("response", otpValidated.getOtpResponse());
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    @Override
    public ResponseEntity<?> registerHDBankAccount(HDBankRegister hdBankRegister) {
        // Validate này kia;
        String credential = credential(hdBankRegister.getUsername(), hdBankRegister.getPassword());
        HDBankRegisterData hdBankRegisterData = new HDBankRegisterData(
                credential,
                hdBankConfig.getPublicKey(),
                hdBankRegister.getEmail(),
                hdBankRegister.getFullName(),
                hdBankRegister.getPhone(),
                hdBankRegister.getIdentityNumber()
        );
        ResponseEntity<RegisterResponse> registerResponse = hdBankRequest.register(hdBankRegisterData);

        if (registerResponse.getStatusCode() == HttpStatus.OK) {
            if (registerResponse.getBody().getData().getUserId() != null) {
                String AccountNo = hdBankRequest.linkClient(credential);
                HDBankAccount hdBankAccount = new HDBankAccount(
                        hdBankRegister.getClientID(),
                        registerResponse.getBody().getData().getUserId(),
                        hdBankRegister.getUsername(),
                        hdBankRegister.getPassword(),
                        hdBankRegister.getFullName(),
                        AccountNo,
                        hdBankRegister.getPhone(),
                        hdBankRegister.getEmail(),
                        hdBankRegister.getIdentityNumber()
                );
                return sendOTP(hdBankRegister.getClientID(), hdBankAccount.getPhone(), hdBankAccount);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(registerResponse.getBody());
    }

    public ResponseEntity<?> resendOTP(HDBankResendOTPRequestData hdBankResendOTPRequestData) {
        return ResponseEntity.status(HttpStatus.OK).body(
                twilioOTPService.resendOTPPhone(
                        hdBankResendOTPRequestData.getClientID(),
                        hdBankResendOTPRequestData.getPhone()));
    }

}