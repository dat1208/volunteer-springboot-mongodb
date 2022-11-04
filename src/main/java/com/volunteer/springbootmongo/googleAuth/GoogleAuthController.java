package com.volunteer.springbootmongo.googleAuth;

import com.volunteer.springbootmongo.models.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
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

    @GetMapping("/principle")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }
    @GetMapping("/")
    public Principal user(Principal principal) {
        return principal;
    }
    @GetMapping("/getGoogleAuthToken")
    public ResponseEntity<ResponseObject> getGoogleAuthToken() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}