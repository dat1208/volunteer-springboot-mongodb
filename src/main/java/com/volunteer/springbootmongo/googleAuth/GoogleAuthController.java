package com.volunteer.springbootmongo.googleAuth;

import com.volunteer.springbootmongo.config.JwtTokenUtil;
import com.volunteer.springbootmongo.models.jwt.JwtResponse;
import com.volunteer.springbootmongo.models.response.ResponseObject;
import com.volunteer.springbootmongo.models.response.ResponseUser;
import com.volunteer.springbootmongo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
/**
 * @author "KhaPhan" on 02-Nov-22
 * @project volunteer-springboot-mongodb
 */
@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth/google")

public class GoogleAuthController {

    @Autowired
    private GoogleAuthService googleAuthService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @PostMapping("/")
    public ResponseEntity<?> receiveAccessToken(@RequestBody GoogleUser googleUser) {
        System.out.println(googleUser.getEmail());
        googleAuthService.storedUser(googleUser);
        String token = jwtTokenUtil.generateToken(googleUser.getEmail());
        ResponseUser responseUser = userService.getUserByUsername(googleUser.getEmail());
        return ResponseEntity.ok(new JwtResponse(true, HttpStatus.OK,"Credential is valid", token, responseUser));
    }
}