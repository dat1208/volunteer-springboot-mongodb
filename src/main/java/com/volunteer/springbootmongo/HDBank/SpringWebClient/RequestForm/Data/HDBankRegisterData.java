package com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HDBankRegisterData {
    private String credential;
    private String key;
    private String email;
    private String fullName;
    private String identityNumber;
    private String phone;
}
