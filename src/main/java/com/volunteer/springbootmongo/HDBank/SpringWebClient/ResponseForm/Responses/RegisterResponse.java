package com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Responses;

import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Data.HDBankRegisterResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Response response;
    private HDBankRegisterResponseData data;
}
