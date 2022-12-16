package com.volunteer.springbootmongo.HDBank.AppsClient.ClientResponse.ResponseData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "KhaPhan" on 15-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppsChangePasswordResponseData {
    private String clientID;
    private String HDBankUsername;
}
