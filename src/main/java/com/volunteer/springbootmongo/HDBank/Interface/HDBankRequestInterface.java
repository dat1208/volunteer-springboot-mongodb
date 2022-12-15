package com.volunteer.springbootmongo.HDBank.Interface;

import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.*;
import org.springframework.http.ResponseEntity;

/**
 * @author "KhaPhan" on 12-Dec-22
 * @project volunteer-springboot-mongodb
 */
public interface HDBankRequestInterface {
    void getHDBankPublicKey();
    void getAccessToken();
    String linkClient(String credential);
    ResponseEntity<?> register(HDBankRegisterData hdBankRegisterData);
    ResponseEntity<?> ChangePasswordHDBankAccount(HDBankChangePasswordRequestData hdBankChangePasswordRequestData);
    ResponseEntity<?> transferHDBankAccount(HDBankTransferRequestData hdBankTransferRequestData);
    ResponseEntity<?> getBalanceHDBankAccount(HDBankBalanceRequestData hdBankBalanceRequestData);
    ResponseEntity<?> getTransferHistoryHDBankAccount(HDBankTransHisRequestData hdBankTransHisRequestData);
    ResponseEntity<?> getTuitionList(HDBankTuitionListRequestData hdBankTuitionListRequestData);
    ResponseEntity<?> paymentTuition(HDBankPaymentTuitionRequestData hdBankPaymentTuitionRequestData);
}
