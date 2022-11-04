package com.volunteer.springbootmongo.service.jwt;
import java.util.ArrayList;

import com.volunteer.springbootmongo.repository.UserRepository;
import com.volunteer.springbootmongo.service.user.UserService;
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
            return new User(username,userService.getPassword(username), new ArrayList<>());
        } else {

            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
