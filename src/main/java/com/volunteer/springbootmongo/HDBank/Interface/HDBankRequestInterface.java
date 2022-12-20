package com.volunteer.springbootmongo.HDBank.Interface;

import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.AppsChangePasswordRequestData;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.*;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.*;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Responses.*;
import org.springframework.http.ResponseEntity;

/**
 * @author "KhaPhan" on 12-Dec-22
 * @project volunteer-springboot-mongodb
 */
public interface HDBankRequestInterface {
    void getHDBankPublicKey();
    void getAccessToken();
    String linkClient(String credential);
    ResponseEntity<?> register(RegisterData registerData);
    ResponseEntity<?> ChangePasswordHDBankAccount(ChangePasswordRequest changePasswordRequest);
    ResponseEntity<TransferResponse> transferHDBankAccount(TransferRequest transferRequest);
    ResponseEntity<BalanceResponse> getBalanceHDBankAccount(BalanceRequest balanceRequest);
    ResponseEntity<TransHisResponse> getTransferHistoryHDBankAccount(TransferHistoryRequest transferHistoryRequest);
    ResponseEntity<TuitionListResponse> getTuitionList(TuitionListRequest tuitionListRequest);
    ResponseEntity<PaymentTuitionResponse> paymentTuition(TuitionPaymentRequest tuitionPaymentRequest);
}
