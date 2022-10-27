package com.volunteer.springbootmongo.service;

import com.volunteer.springbootmongo.models.LoginForm;
import com.volunteer.springbootmongo.models.User;
import com.volunteer.springbootmongo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    private boolean userNameVal(String userName){
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }

    //Get all users from mongodb
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean authAccount(LoginForm loginForm){
        System.out.println(loginForm.getUsername());
        System.out.println(loginForm.getPassword());
        System.out.println("-----");

        //Validate Email
        if( userNameVal(loginForm.getUsername()) == true){
            User user = new User();
             userRepository.findUserByEmail(loginForm.getUsername()).ifPresent(u -> {
                user.setPwd(u.getPwd());
             });

             if(Objects.equals(user.getPwd(), loginForm.getPassword()))
               return true;
             else return false;
        }
        //Else is phone number
        else if(userNameVal(loginForm.getUsername()) == false) {
            User user = new User();
            userRepository.findUserByPhonenumber(loginForm.getUsername()).ifPresent(u -> {
                user.setPwd(u.getPwd());
            });

            if(Objects.equals(user.getPwd(), loginForm.getPassword()))
                return true;
            else return false;
        }
        return false;
    }
}
