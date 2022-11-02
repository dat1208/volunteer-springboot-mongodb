package com.volunteer.springbootmongo.controller.user;

import com.volunteer.springbootmongo.models.LoginForm;
import com.volunteer.springbootmongo.models.RegisterForm;
import com.volunteer.springbootmongo.models.data.User;
import com.volunteer.springbootmongo.models.response.ResponseObject;
import com.volunteer.springbootmongo.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/getAllUsers")
    //Get all users method
    public List<User> fetchAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping ("/v1/users/auth")
    public boolean auth(@RequestBody LoginForm loginForm){
       return userService.authAccount(loginForm);
    }

    @PostMapping ("/v2/users/auth")
    public ResponseEntity<ResponseObject> authv2(@RequestBody LoginForm loginForm){
        //new ResponseObject((HttpStatus.OK.toString()),userService.authAccountv2(loginForm)),HttpStatus.OK
        return new ResponseEntity<>(userService.authAccountv2(loginForm),HttpStatus.OK);
    }

    @PostMapping ("/v1/users/register")
    public ResponseEntity<ResponseObject> register(@RequestBody RegisterForm registerForm){
       return new ResponseEntity<>(userService.register(registerForm),HttpStatus.OK);
    }
}
