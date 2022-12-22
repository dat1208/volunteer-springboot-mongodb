package com.volunteer.springbootmongo.HDBank.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.*;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientResponse.AppsChangePasswordResponse;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientResponse.ResponseData.AppsChangePasswordResponseData;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientResponse.ResponseForm.Message;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientResponse.ResponseForm.Response;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientResponse.ResponseForm.Status;
import com.volunteer.springbootmongo.HDBank.Interface.HDBankUserInterface;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPResponse;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPStatus;
import com.volunteer.springbootmongo.HDBank.Service.OTP.RequestForm.OTPValidated;
import com.volunteer.springbootmongo.HDBank.Service.OTP.TwilioOTPService;
import com.volunteer.springbootmongo.HDBank.Service.RSA.ChangePasswordMessage;
import com.volunteer.springbootmongo.HDBank.Service.RSA.LoginMessage;
import com.volunteer.springbootmongo.HDBank.Service.RSA.RSAKeyPairGenerator;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.HDBankRequest.HDBankConfig;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.HDBankRequest.HDBankRequest;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.ChangePasswordRequest;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.ChangePasswordRequestData;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.RegisterData;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Responses.RegisterResponse;
import com.volunteer.springbootmongo.models.data.HDBankAccount;
import com.volunteer.springbootmongo.models.data.User;
import com.volunteer.springbootmongo.repository.UserRepository;
import com.volunteer.springbootmongo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author "KhaPhan" on 12-Dec-22
 * @project volunteer-springboot-mongodb
 */
@RequiredArgsConstructor
@Service
public class HDBankService implements HDBankUserInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(HDBankService.class);
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final HDBankConfig hdBankConfig;
    @Autowired
    private final HDBankRequest hdBankRequest;
    @Autowired
    private TwilioOTPService twilioOTPService;
    @Autowired
    private ValidateService validateService;
    @Autowired
    UserService userService;
    @Scheduled(fixedDelay = 180000)
    public void refreshTokenTask() {
        LOGGER.info("TASK REFRESH TOKEN RUNNING - CALL NEW ACCESS TOKEN");
        hdBankRequest.getAccessToken();
    }

    private String credential(String message) {
        if (hdBankConfig.getPublicKey().equals("")) {
            hdBankRequest.getHDBankPublicKey();
        }
        String credential = "";
        try {
            credential = RSAKeyPairGenerator.getEncoderRSA(message, hdBankConfig.getPublicKey());
        } catch (IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return credential;
    }
    private String getJsonFromObject(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonGenerationException | JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
    public ResponseEntity<?> sendOTP(String clientID, String phone, Object data) {
        OTPResponse otpResponse = twilioOTPService.sendOTPPhone(clientID, phone, data);

        Map<String, Object> verifyMessage = new HashMap<>();
        verifyMessage.put("clientID", clientID);
        verifyMessage.put("otp", otpResponse);
        if (otpResponse.getStatus().equals(OTPStatus.SUCCESS)) {
            verifyMessage.put("verifyURL", "/api/HDBank/verify");
        }
        return ResponseEntity.status(HttpStatus.OK).body(verifyMessage);
    }
    public ResponseEntity<?> LinkHDBankAccount(AppsLoginRequestData AppsLoginRequestData) {
        Map<String, Object> ResponseMessage = new HashMap<>();

        if (!userService.phoneVal(AppsLoginRequestData.getPhone())) {
            ResponseMessage.put("message", Message.the_Phone_number_is_wrong_format);
            return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage);
        }

        LoginMessage loginMessage = new LoginMessage(AppsLoginRequestData.getUsername(), AppsLoginRequestData.getPassword());
        String credential = credential(getJsonFromObject(loginMessage));

        String AccountNo = hdBankRequest.linkClient(credential);

        HDBankAccount hdBankAccount = new HDBankAccount();
        hdBankAccount.setHDBankUsername(AppsLoginRequestData.getUsername());
        hdBankAccount.getHDBankOldPassword().add(AppsLoginRequestData.getPassword());
        hdBankAccount.setAccountNumber(AccountNo);
        hdBankAccount.setUserID(AppsLoginRequestData.getClientID());


        if (!AccountNo.isEmpty()) {
            if (validateService.isOverLinkedAccount(AppsLoginRequestData.getClientID())) {
                ResponseMessage.put("message", Message.current_user_is_limit_link_bank_account);
                return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage);
            }
            if (validateService.isExistLinked(AppsLoginRequestData.getClientID(), AccountNo)) {
                ResponseMessage.put("message", Message.this_HDBank_account_linked_please_choose_another_account);
                return ResponseEntity.status(HttpStatus.OK).body(ResponseMessage);
            }
            return sendOTP(AppsLoginRequestData.getClientID(), AppsLoginRequestData.getPhone(), hdBankAccount);
        }
        ResponseMessage.put("HDBankUser", AppsLoginRequestData.getUsername());
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
    public ResponseEntity<?> verifyOTP(AppsOTPVerifyRequestData appsOtpVerifyRequestData) {

        OTPValidated otpValidated = twilioOTPService.validateOTP(appsOtpVerifyRequestData.getOTP(), appsOtpVerifyRequestData.getClientID());
        Map<String, Object> responseMessage = new HashMap<>();

        if (otpValidated.getHdBankAccount() != null) {
            boolean isStoredHDBankAccount = storedHDBankAccount(appsOtpVerifyRequestData.getClientID(), otpValidated.getHdBankAccount());
            if (isStoredHDBankAccount) {
                twilioOTPService.removeOTP(appsOtpVerifyRequestData.getClientID());
                responseMessage.put("request", appsOtpVerifyRequestData);
                responseMessage.put("response", otpValidated.getOtpResponse());
                return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
            }
        }
        responseMessage.put("response", otpValidated.getOtpResponse());
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
    @Override
    public ResponseEntity<?> registerHDBankAccount(AppsRegisterRequestData appsRegisterRequestData) {
        // Validate này kia;
        if (!validateService.isValidatedRegisterInput(appsRegisterRequestData)) {
            Map<String, Object> responseMessage = new HashMap<>();
            responseMessage.put("status", "INVALID_DATA");
            responseMessage.put("message", "request_data_is_wrong_format_check_document_again");
            ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        }
        LoginMessage loginMessage = new LoginMessage(appsRegisterRequestData.getUsername(), appsRegisterRequestData.getPassword());
        String credential = credential(getJsonFromObject(loginMessage));
        RegisterData registerData = new RegisterData(
                credential,
                hdBankConfig.getPublicKey(),
                appsRegisterRequestData.getEmail(),
                appsRegisterRequestData.getFullName(),
                appsRegisterRequestData.getPhone(),
                appsRegisterRequestData.getIdentityNumber()
        );
        ResponseEntity<RegisterResponse> registerResponse = hdBankRequest.register(registerData);

        if (registerResponse.getStatusCode() == HttpStatus.OK) {
            if (registerResponse.getBody().getData().getUserId() != null) {
                String AccountNo = hdBankRequest.linkClient(credential);
                HDBankAccount hdBankAccount = new HDBankAccount(
                        appsRegisterRequestData.getClientID(),
                        registerResponse.getBody().getData().getUserId(),
                        appsRegisterRequestData.getUsername(),
                        appsRegisterRequestData.getPassword(),
                        appsRegisterRequestData.getFullName(),
                        AccountNo,
                        appsRegisterRequestData.getPhone(),
                        appsRegisterRequestData.getEmail(),
                        appsRegisterRequestData.getIdentityNumber()
                );
                hdBankAccount.getHDBankOldPassword().add(appsRegisterRequestData.getPassword());
                return sendOTP(appsRegisterRequestData.getClientID(), hdBankAccount.getPhone(), hdBankAccount);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(registerResponse.getBody());
    }
    public ResponseEntity<?> resendOTP(AppsResendOTPRequestData appsResendOTPRequestData) {
        return ResponseEntity.status(HttpStatus.OK).body(
                twilioOTPService.resendOTPPhone(
                        appsResendOTPRequestData.getClientID(),
                        appsResendOTPRequestData.getPhone()));
    }
    public ResponseEntity<?> changePasswordHDBankAccount(AppsChangePasswordRequestData appsChangePasswordRequestData) {
        AppsChangePasswordResponse appsChangePasswordResponse = new AppsChangePasswordResponse();
        appsChangePasswordResponse.setData(
                new AppsChangePasswordResponseData(appsChangePasswordRequestData.getClientID(), appsChangePasswordRequestData.getHdBankUsername()));

        if (validateService.isValidPasswordType(appsChangePasswordRequestData.getNewPassword())) {
            appsChangePasswordResponse.setResponse(new Response(Status.WRONG_FORMAT, Message.new_password_is_wrong_format));
            return ResponseEntity.status(HttpStatus.OK).body(appsChangePasswordResponse);
        }

        User currentUser = userRepository.findById(appsChangePasswordRequestData.getClientID()).get();
        int currentHDBankAccountIndex = 0;
        if (currentUser != null) {
            if (currentUser.getHdBankAccountList() != null) {
                List<HDBankAccount> HBankAccountList = currentUser.getHdBankAccountList().stream().toList();

                for (HDBankAccount hdBankAccountStored : HBankAccountList) {
                    if (hdBankAccountStored.getHDBankUsername().equals(appsChangePasswordRequestData.getHdBankUsername())) {
                        if (hdBankAccountStored.getHDBankOldPassword() != null) {

                            if (validateService.isMatchWithOldPassword(
                                    hdBankAccountStored.getHDBankOldPassword(),
                                    appsChangePasswordRequestData.getNewPassword())) {

                                appsChangePasswordResponse.setResponse(
                                        new Response(
                                                Status.FAILURE,
                                                Message.new_password_is_used_in_a_previous_time_try_another_password));

                                return ResponseEntity.status(HttpStatus.OK).body(appsChangePasswordResponse);
                            }
                        }
                        break;
                    }
                    currentHDBankAccountIndex += 1;
                }
            }
        }
        ChangePasswordMessage changePassword =
                new ChangePasswordMessage(
                        appsChangePasswordRequestData.getHdBankUsername(),
                        appsChangePasswordRequestData.getOldPassword(),
                        appsChangePasswordRequestData.getNewPassword());

        String credential = credential(getJsonFromObject(changePassword));

        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setData(new ChangePasswordRequestData(
                credential,
                hdBankConfig.getPublicKey()
        ));
        boolean isChangePasswordSuccess = hdBankRequest.ChangePasswordHDBankAccount(changePasswordRequest).getBody().getResponse().getResponseCode() == "00";
        if (isChangePasswordSuccess) {
            try {
                List<HDBankAccount> hdBankAccountList = currentUser.getHdBankAccountList();
                if(hdBankAccountList.size() > 4) {
                    hdBankAccountList
                            .get(currentHDBankAccountIndex)
                            .getHDBankOldPassword().remove(0);
                }
                hdBankAccountList
                        .get(currentHDBankAccountIndex)
                        .getHDBankOldPassword()
                        .add(appsChangePasswordRequestData.getNewPassword());

                userRepository.save(currentUser);

            } catch (Exception ex) {
                LOGGER.error("Stored Old Password Error: {}", ex);
                appsChangePasswordResponse.setResponse(
                        new Response(Status.SERVER_ERROR, Message.valueOf(ex.getMessage())));

                return ResponseEntity.status(HttpStatus.OK).body(appsChangePasswordResponse);
            }
            appsChangePasswordResponse.setResponse(new Response(Status.SUCCESS, Message.change_password_success_login_again_please));
        }
        return ResponseEntity.status(HttpStatus.OK).body(appsChangePasswordResponse);
    }

    public ResponseEntity<?> getBalance(AppsGetBalanceRequest appsGetBalanceRequest) {
        return null;
    }

    public ResponseEntity<?> getAccount(String clientID) {
        Optional<User> user =  userRepository.findById(clientID).stream().findFirst();
        Map<String, Object> responseMessage = new HashMap<>();
        responseMessage.put("clientID", clientID);
        if(!(user == null)) {
            if(!(user.get().getHdBankAccountList() == null)) {
                HDBankAccount hdBankAccount = user.get().getHdBankAccountList().get(0);
                responseMessage.put("hdBankAccountUsername", hdBankAccount.getHDBankUsername());
                responseMessage.put("hdBankAccountAccNumber", hdBankAccount.getAccountNumber());
                responseMessage.put("responseStatus", "SUCCESS");
                responseMessage.put("message", "get_bank_account_success");
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }
        }
        responseMessage.put("responseStatus", "FAILURE");
        responseMessage.put("message", "get_bank_account_failure_account_not_linked_yet");
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}