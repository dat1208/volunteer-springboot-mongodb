package com.volunteer.springbootmongo.HDBank.AppsClient.ClientResponse;

import com.volunteer.springbootmongo.HDBank.AppsClient.ClientResponse.ResponseData.AppsChangePasswordResponseData;
import com.volunteer.springbootmongo.HDBank.AppsClient.ClientResponse.ResponseForm.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author "KhaPhan" on 15-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AppsChangePasswordResponse {
    private Response response;
    private AppsChangePasswordResponseData data;
}
