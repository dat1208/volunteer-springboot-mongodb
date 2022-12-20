package com.volunteer.springbootmongo.HDBank.SpringWebClient.HDBankRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.N;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Service
public class HDBankConfig {
    @Value("${hdBank.env.x-api-key}")
    private String HDBankOpenApiKey;

    @Value("${hdBank.env.client-id}")
    private String HDBankClientID;

    private String HDBankOpenApiAccessToken;

    @Value("${hdBank.env.refresh-token}")
    private String HDBankOpenApiRefreshToken;

    @Value("${hdBank.env.public-key}")
    private String PublicKey;

    @Value("${hdBank.env.base-url}")
    private String HDBankOpenApiBaseURL;

    @Value("${hdBank.env.oauth-url}")
    private String HDBankOpenAPIOauthBaseURL;
}
