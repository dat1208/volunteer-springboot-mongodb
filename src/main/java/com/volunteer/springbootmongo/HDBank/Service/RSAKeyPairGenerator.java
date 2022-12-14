package com.volunteer.springbootmongo.HDBank.Service;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import org.springframework.web.client.RestTemplate;
/**
 * @author "KhaPhan" on 12-Dec-22
 * @project volunteer-springboot-mongodb
 */
public class RSAKeyPairGenerator {
    private static final String RSAKeyPairGeneratorBaseURL = "http://rsa.somee.com/api/rsa";

    public static String getRSAKeyPairGenerator(String username, String password, String publicKey) {
        System.out.println(publicKey);
        RestTemplate restTemplate = new RestTemplate();

        String credential = "";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestBody = new JSONObject();
        requestBody.put("username", username);
        requestBody.put("password", password);
        requestBody.put("key", publicKey);

        HttpEntity<JSONObject> entity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<String> response = restTemplate.exchange(RSAKeyPairGeneratorBaseURL, HttpMethod.POST, entity, String.class);
        if(response.getStatusCode() ==  HttpStatus.OK && !response.getBody().isEmpty()) {
            credential = response.getBody();
        }
        return credential;
    }
}