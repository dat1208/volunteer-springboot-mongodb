package com.volunteer.springbootmongo.HDBank.SpringWebClient.HDBankRequest;

import com.volunteer.springbootmongo.HDBank.Interface.HDBankRequestInterface;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.HDBankRegisterData;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.HDBankRegisterRequest;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Login;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Request;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Response.LoginResponse;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Response.PublicKeyResponse;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Response.RefreshTokenResponse;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Response.RegisterResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author "KhaPhan" on 12-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Getter
@Setter
@AllArgsConstructor
@Service
public class HDBankRequest implements HDBankRequestInterface {
    @Autowired
    private final HDBankConfig hdBankConfig;
    private HttpHeaders getDefaultHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Access-token", hdBankConfig.getHDBankOpenApiAccessToken());
        headers.set("X-api-key", hdBankConfig.getHDBankOpenApiKey());
        return headers;
    }
    @Override
    public void getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> bodyParam = new LinkedMultiValueMap<>();
        bodyParam.add("client_id", hdBankConfig.getHDBankClientID());
        bodyParam.add("grant_type", "refresh_token");
        bodyParam.add("refresh_token", hdBankConfig.getHDBankOpenApiRefreshToken());

        HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(bodyParam, headers);
        ResponseEntity<RefreshTokenResponse> response =
                restTemplate.exchange(hdBankConfig.getHDBankOpenAPIOauthBaseURL(), HttpMethod.POST, httpEntity, RefreshTokenResponse.class);

        if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {

        } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            //TODO: Login Đạt RefreshToken;
        }
    }

    @Override
    public void getHDBankPublicKey() {
        RestTemplate restTemplate = new RestTemplate();

        String publicKeyURL = hdBankConfig.getHDBankOpenApiBaseURL() + "/get_key";

        HttpEntity<PublicKeyResponse> httpEntity = new HttpEntity<>(getDefaultHeader());
        try {
            ResponseEntity<PublicKeyResponse> response =
                    restTemplate.exchange(publicKeyURL, HttpMethod.GET, httpEntity, PublicKeyResponse.class);
            if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
                // setPublicKey(response.getBody().getData().getKey());
            }
        } catch (Exception ex) {
            getAccessToken();
        }
    }

    @Override
    public String linkClient(String credential) {
        RestTemplate restTemplate = new RestTemplate();
        String LoginURL = hdBankConfig.getHDBankOpenApiBaseURL() + "/login";

        JSONObject requestBody = new JSONObject();
        requestBody.put("request", new Request());
        requestBody.put("data", new Login(credential, hdBankConfig.getPublicKey()));

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(requestBody, getDefaultHeader());

        ResponseEntity<LoginResponse> response =
                restTemplate.exchange(LoginURL, HttpMethod.POST, httpEntity, LoginResponse.class);
        if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
            if (response.getBody().getData().getAccountNo() != null) {
                System.out.println("LOGIN: "+ response.getBody().getData().getAccountNo());
                return response.getBody().getData().getAccountNo();
            }
        } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            //TODO: Đạt Run new request
        }
        return "";
    }

    @Override
    public ResponseEntity<RegisterResponse> register(HDBankRegisterData hdBankRegisterdata) {
        RestTemplate restTemplate = new RestTemplate();
        String registerURL = hdBankConfig.getHDBankOpenApiBaseURL() + "/register";
        HDBankRegisterRequest hdBankRegisterRequest = new HDBankRegisterRequest(new Request(), hdBankRegisterdata);
        return restTemplate.exchange(registerURL, HttpMethod.POST, new HttpEntity<>(hdBankRegisterRequest, getDefaultHeader()), RegisterResponse.class);
    }
}