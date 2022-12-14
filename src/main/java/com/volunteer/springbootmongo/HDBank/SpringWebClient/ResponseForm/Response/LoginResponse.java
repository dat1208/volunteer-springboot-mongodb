package com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Response;

import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Data.HDBankLoginResponseData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class LoginResponse {
    private Response response;
    private HDBankLoginResponseData data;
}
