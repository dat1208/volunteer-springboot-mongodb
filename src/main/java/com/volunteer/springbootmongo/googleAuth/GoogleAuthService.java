package com.volunteer.springbootmongo.googleAuth;

import com.google.type.DateTime;
import com.volunteer.springbootmongo.models.data.TypeClient;
import com.volunteer.springbootmongo.models.data.User;
import com.volunteer.springbootmongo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author "KhaPhan" on 05-Nov-22
 * @project volunteer-springboot-mongodb
 */
@Service
public class GoogleAuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    // TODO: Stored User to DB
    public void storedUser(GoogleUser googleUser) {
        User user = new User();

        user.setEmail(googleUser.getEmail());
        user.setLastname(googleUser.getName());
        user.setAvatar(googleUser.getPicture());
        user.setPwd(passwordEncoder.encode(googleUser.getId()));
        try {
            userService.insertUser(user, TypeClient.single.toString());
        } catch (Exception ex) {
            throw ex;
        }
    }
    public void getGoogleUser() {

    }
}
