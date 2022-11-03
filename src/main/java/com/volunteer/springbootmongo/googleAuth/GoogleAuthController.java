package com.volunteer.springbootmongo.googleAuth;

import com.volunteer.springbootmongo.models.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author "KhaPhan" on 02-Nov-22
 * @project volunteer-springboot-mongodb
 */
@RestController
@RequestMapping("/")
public class GoogleAuthController {
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping
    public Map<String, Object> currentUserLogin(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        return oAuth2AuthenticationToken.getPrincipal().getAttributes();
    }

    @GetMapping("/getGoogleAuthToken")
    public ResponseEntity<ResponseObject> getGoogleAuthToken() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}