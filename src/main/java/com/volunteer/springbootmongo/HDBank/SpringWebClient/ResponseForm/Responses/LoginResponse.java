package com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Responses;

import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Data.HDBankLoginResponseData;
import lombok.*;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class LoginResponse {
    private Response response;
    private HDBankLoginResponseData data;
}
