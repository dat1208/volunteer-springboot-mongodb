package com.volunteer.springbootmongo.HDBank.Interface;

import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.HDBankRegisterData;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Response.RegisterResponse;
import org.springframework.http.ResponseEntity;

/**
 * @author "KhaPhan" on 12-Dec-22
 * @project volunteer-springboot-mongodb
 */
public interface HDBankRequestInterface {
    void getHDBankPublicKey();
    void getAccessToken();
    String linkClient(String credential);
    ResponseEntity<RegisterResponse> register(HDBankRegisterData hdBankRegisterData);
}
