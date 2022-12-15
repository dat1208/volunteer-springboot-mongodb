package com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Response;

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
public class RefreshTokenResponse {
    private String id_token;
    private String access_token;
    private int expires_in;
    private String token_type;
}
