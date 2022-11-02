package com.volunteer.springbootmongo.service;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.volunteer.springbootmongo.config.EncoderConfig;
import com.volunteer.springbootmongo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (userService.checkUsername(username)) {
            System.err.println(passwordEncoder.encode(userService.getPassword(username)));
            return new User(username,passwordEncoder.encode(userService.getPassword(username)),
                    new ArrayList<>());
        } else {

            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
