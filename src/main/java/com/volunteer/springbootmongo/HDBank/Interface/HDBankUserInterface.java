package com.volunteer.springbootmongo.HDBank.Interface;

import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.HDBankAccountRequestData;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.HDBankRegister;
import org.springframework.http.ResponseEntity;

/**
 * @author "KhaPhan" on 12-Dec-22
 * @project volunteer-springboot-mongodb
 */
public interface HDBankUserInterface {
    ResponseEntity<?> LinkHDBankAccount(HDBankAccountRequestData HDBankAccountRequestData);

    ResponseEntity<?>  registerHDBankAccount(HDBankRegister hdBankRegister);
}
