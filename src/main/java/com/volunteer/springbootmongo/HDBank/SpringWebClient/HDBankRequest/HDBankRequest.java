package com.volunteer.springbootmongo.HDBank.SpringWebClient.HDBankRequest;

import com.volunteer.springbootmongo.HDBank.Interface.HDBankRequestInterface;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.*;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.*;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Responses.*;
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
        try {
            ResponseEntity<LoginResponse> response =
                    restTemplate.exchange(LoginURL, HttpMethod.POST, httpEntity, LoginResponse.class);
            if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
                if (response.getBody().getData().getAccountNo() != null) {
                    System.out.println("LOGIN: " + response.getBody().getData().getAccountNo());
                    return response.getBody().getData().getAccountNo();
                }
            } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                //TODO: Đạt Run new request
            }
        } catch (Exception ex) {
            System.out.println("Link HDBank Account Ex: " + ex);
        }
        return "";
    }

    @Override
    public ResponseEntity<RegisterResponse> register(RegisterData registerdata) {
        RestTemplate restTemplate = new RestTemplate();
        String registerURL = hdBankConfig.getHDBankOpenApiBaseURL() + "/register";
        RegisterRequest registerRequest = new RegisterRequest(new Request(), registerdata);
        return restTemplate.exchange(registerURL, HttpMethod.POST, new HttpEntity<>(registerRequest, getDefaultHeader()), RegisterResponse.class);
    }

    @Override
    public ResponseEntity<ChangePasswordResponse> ChangePasswordHDBankAccount(ChangePasswordRequest changePasswordRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String registerURL = hdBankConfig.getHDBankOpenApiBaseURL() + "/change_password";
        return restTemplate.exchange(registerURL, HttpMethod.POST, new HttpEntity<>(changePasswordRequest, getDefaultHeader()), ChangePasswordResponse.class);
    }

    @Override
    public ResponseEntity<TransferResponse> transferHDBankAccount(TransferRequest transferRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String registerURL = hdBankConfig.getHDBankOpenApiBaseURL() + "/transfer";
        return restTemplate.exchange(registerURL, HttpMethod.POST, new HttpEntity<>(transferRequest, getDefaultHeader()), TransferResponse.class);
    }

    @Override
    public ResponseEntity<BalanceResponse> getBalanceHDBankAccount(BalanceRequest balanceRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String registerURL = hdBankConfig.getHDBankOpenApiBaseURL() + "/balance";
        return restTemplate.exchange(registerURL, HttpMethod.POST, new HttpEntity<>(balanceRequest, getDefaultHeader()), BalanceResponse.class);
    }

    @Override
    public ResponseEntity<TransHisResponse> getTransferHistoryHDBankAccount(TransferHistoryRequest transferHistoryRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String registerURL = hdBankConfig.getHDBankOpenApiBaseURL() + "/tranhis";
        return restTemplate.exchange(registerURL, HttpMethod.POST, new HttpEntity<>(transferHistoryRequest, getDefaultHeader()), TransHisResponse.class);
    }

    @Override
    public ResponseEntity<TuitionListResponse> getTuitionList(TuitionListRequest tuitionListRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String registerURL = hdBankConfig.getHDBankOpenApiBaseURL() + "/getpayment";
        return restTemplate.exchange(registerURL, HttpMethod.POST, new HttpEntity<>(tuitionListRequest, getDefaultHeader()), TuitionListResponse.class);
    }

    @Override
    public ResponseEntity<PaymentTuitionResponse> paymentTuition(TuitionPaymentRequest tuitionPaymentRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String registerURL = hdBankConfig.getHDBankOpenApiBaseURL() + "/getpayment";
        return restTemplate.exchange(registerURL, HttpMethod.POST, new HttpEntity<>(tuitionPaymentRequest, getDefaultHeader()), PaymentTuitionResponse.class);
    }
}