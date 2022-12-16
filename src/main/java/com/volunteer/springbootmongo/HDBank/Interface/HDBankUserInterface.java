package com.volunteer.springbootmongo.HDBank.Interface;

import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.AppsLoginRequestData;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientRequest.AppsRegisterRequestData;
import org.springframework.http.ResponseEntity;

/**
 * @author "KhaPhan" on 12-Dec-22
 * @project volunteer-springboot-mongodb
 */
public interface HDBankUserInterface {
    ResponseEntity<?> LinkHDBankAccount(AppsLoginRequestData AppsLoginRequestData);
    ResponseEntity<?>  registerHDBankAccount(AppsRegisterRequestData appsRegisterRequestData);
}
