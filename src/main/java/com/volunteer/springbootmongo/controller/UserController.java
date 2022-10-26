package com.volunteer.springbootmongo.controller;

import com.volunteer.springbootmongo.models.LoginForm;
import com.volunteer.springbootmongo.models.ResponseObject;
import com.volunteer.springbootmongo.models.User;
import com.volunteer.springbootmongo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getAllUsers")
    //Get all users method
    public List<User> fetchAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping ("/auth")
    public boolean auth(@RequestBody LoginForm loginForm){
       return userService.authAccount(loginForm);
    }
}
