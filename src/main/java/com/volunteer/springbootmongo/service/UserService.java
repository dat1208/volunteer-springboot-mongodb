package com.volunteer.springbootmongo.service;

import com.volunteer.springbootmongo.models.LoginForm;
import com.volunteer.springbootmongo.models.RegisterForm;
import com.volunteer.springbootmongo.models.data.Account;
import com.volunteer.springbootmongo.models.data.Account_User;
import com.volunteer.springbootmongo.models.data.Permission_Type;
import com.volunteer.springbootmongo.models.data.Permission;
import com.volunteer.springbootmongo.models.data.User;
import com.volunteer.springbootmongo.models.response.ResponseObject;
import com.volunteer.springbootmongo.models.response.ResponseUser;
import com.volunteer.springbootmongo.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final Account_UserRepo account_userRepo;
    @Autowired
    private final Permission_TypeRepo permission_typeRepo;

    @Autowired
    private final PermissionRepository permissionRepository;

    //Regex validate email
    private boolean emailVal(String userName){
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }
    //Regex validate password
    //Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
    private boolean passwordVal(String password){
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
    //Regex validate phone number
    private boolean phoneVal(String phonenumber){
        String regex = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phonenumber);
        return matcher.matches();
    }

    //Get all users from mongodb
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean authAccount(LoginForm loginForm){
        //Validate Email
        if(emailVal(loginForm.getUsername())){
            User user = new User();
             userRepository.findUserByEmail(loginForm.getUsername()).ifPresent(u -> {
                user.setPwd(u.getPwd());
             });

             if(Objects.equals(user.getPwd(), loginForm.getPassword()))
               return true;
             else return false;
        }
        //Else is phone number
        else if(phoneVal(loginForm.getUsername())) {
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

    public ResponseObject authAccountv2(LoginForm loginForm){
        String error = "Failure";
        //Validate Email
        if(emailVal(loginForm.getUsername())){
            User user = new User();
            userRepository.findUserByEmail(loginForm.getUsername()).ifPresent(u -> {
                user.setPwd(u.getPwd());
                user.setFirstname(u.getFirstname());
                user.setEmail(u.getEmail());
                user.setLastname(u.getLastname());
                user.setPhonenumber(u.getPhonenumber());
            });

            if(Objects.equals(user.getPwd(), loginForm.getPassword()))
            {
                ResponseUser responseUser = new ResponseUser(user.getFirstname(),user.getLastname(),user.getEmail(),user.getPhonenumber());
                return new ResponseObject(HttpStatus.ACCEPTED.toString(), responseUser);
            }
            else return new ResponseObject(HttpStatus.NOT_FOUND.toString(), error);
        }
        //Else is phone number
        else if(phoneVal(loginForm.getUsername())) {
            User user = new User();

            userRepository.findUserByPhonenumber(loginForm.getUsername()).ifPresent(u -> {
                user.setPwd(u.getPwd());
                user.setFirstname(u.getFirstname());
                user.setEmail(u.getEmail());
                user.setLastname(u.getLastname());
                user.setPhonenumber(u.getPhonenumber());
            });

            if(Objects.equals(user.getPwd(), loginForm.getPassword()))
            {
                ResponseUser responseUser = new ResponseUser(user.getFirstname(),user.getLastname(),user.getEmail(),user.getPhonenumber());
                return new ResponseObject(HttpStatus.ACCEPTED.toString(), responseUser);
            }
            else return new ResponseObject(HttpStatus.NOT_FOUND.toString(), error);
        }
        else return new ResponseObject(HttpStatus.NOT_FOUND.toString(), error);
    }

    public ResponseUser authAccountJwt(LoginForm loginForm){
        String error = "Failure";
        //Validate Email
        if(emailVal(loginForm.getUsername())){
            User user = new User();
            userRepository.findUserByEmail(loginForm.getUsername()).ifPresent(u -> {
                user.setPwd(u.getPwd());
                user.setFirstname(u.getFirstname());
                user.setEmail(u.getEmail());
                user.setLastname(u.getLastname());
                user.setPhonenumber(u.getPhonenumber());
            });

            if(Objects.equals(user.getPwd(), loginForm.getPassword()))
            {
                ResponseUser responseUser = new ResponseUser(user.getFirstname(),user.getLastname(),user.getEmail(),user.getPhonenumber());
                return new ResponseUser(responseUser);
            }
            else return null;
        }
        //Else is phone number
        else if(phoneVal(loginForm.getUsername())) {
            User user = new User();

            userRepository.findUserByPhonenumber(loginForm.getUsername()).ifPresent(u -> {
                user.setPwd(u.getPwd());
                user.setFirstname(u.getFirstname());
                user.setEmail(u.getEmail());
                user.setLastname(u.getLastname());
                user.setPhonenumber(u.getPhonenumber());
            });

            if(Objects.equals(user.getPwd(), loginForm.getPassword()))
            {
                ResponseUser responseUser = new ResponseUser(user.getFirstname(),user.getLastname(),user.getEmail(),user.getPhonenumber());
                return new ResponseUser(responseUser);
            }
            else return null;
        }
        else return null;
    }

    public ResponseUser getUserByUsername(String userName){
        //Validate Email
        if(emailVal(userName)){
            User user = new User();
            userRepository.findUserByEmail(userName).ifPresent(u -> {
                user.setPwd(u.getPwd());
                user.setFirstname(u.getFirstname());
                user.setEmail(u.getEmail());
                user.setLastname(u.getLastname());
                user.setPhonenumber(u.getPhonenumber());
            });
            ResponseUser responseUser = new ResponseUser(user.getFirstname(),user.getLastname(),user.getEmail(),user.getPhonenumber());
            return new ResponseUser(responseUser);
        }
        //Else is phone number
        else if(phoneVal(userName)) {
            User user = new User();

            userRepository.findUserByPhonenumber(userName).ifPresent(u -> {
                user.setPwd(u.getPwd());
                user.setFirstname(u.getFirstname());
                user.setEmail(u.getEmail());
                user.setLastname(u.getLastname());
                user.setPhonenumber(u.getPhonenumber());
            });
            ResponseUser responseUser = new ResponseUser(user.getFirstname(),user.getLastname(),user.getEmail(),user.getPhonenumber());
            return new ResponseUser(responseUser);
        }
        else return null;
    }
    public ResponseObject register(RegisterForm registerForm){
       if(userRepository.findUserByPhonenumber(registerForm.getPhonenumber()).isPresent() && userRepository.findUserByEmail(registerForm.getEmail()).isPresent()){
           String error = "phone_number_and_email_is_exist";
           return new ResponseObject((HttpStatus.FOUND.toString()),error);
       } else if (userRepository.findUserByPhonenumber(registerForm.getPhonenumber()).isPresent()){
           String error = "phone_number_is_exist";
           return new ResponseObject((HttpStatus.FOUND.toString()),error);
       } else if (userRepository.findUserByEmail(registerForm.getEmail()).isPresent()){
           String error = "email_is_exist";
           return new ResponseObject((HttpStatus.FOUND.toString()),error);
       } else if(!phoneVal(registerForm.getPhonenumber())){
           String error = "wrong_phone_number_format";
           return new ResponseObject((HttpStatus.CHECKPOINT.toString()),error);
       } else if (!emailVal(registerForm.getEmail())) {
           String error = "wrong_email_format";
           return new ResponseObject((HttpStatus.CHECKPOINT.toString()),error);
       } else if (!passwordVal(registerForm.getPassword())) {
           String error = "wrong_password_format";
           return new ResponseObject((HttpStatus.CHECKPOINT.toString()),error);
       } else {

            User user = new User(registerForm.getFirstname(), registerForm.getLastname(),
                    registerForm.getEmail(), registerForm.getPhonenumber(), registerForm.getPassword());
           userRepository.insert(user);
           Account account = new Account(user.get_id().toString(),registerForm.getType(),null);
           accountRepository.insert(account);
           Account_User account_user = new Account_User(account.get_id().toString(),false);
           account_userRepo.insert(account_user);
           Permission_Type permission_type = permission_typeRepo.findPermission_TypeByName("User").get();
           Permission permission = new Permission(account_user.get_id().toString(),user.get_id().toString(),permission_type.get_id().toString());
           permissionRepository.insert(permission);
           return new ResponseObject((HttpStatus.CREATED.toString()),user);
       }
    }

    public boolean checkUsername(String username){
        if(emailVal(username)){
            com.volunteer.springbootmongo.models.data.User user = new com.volunteer.springbootmongo.models.data.User();
            return userRepository.findUserByEmail(username).isPresent();

        }
        //Else is phone number
        else if(phoneVal(username)) {
            com.volunteer.springbootmongo.models.data.User user = new com.volunteer.springbootmongo.models.data.User();
            return userRepository.findUserByPhonenumber(username).isPresent();
        }
        return false;
    }

    public String getPassword(String username){
        if(emailVal(username)){
            com.volunteer.springbootmongo.models.data.User user = new com.volunteer.springbootmongo.models.data.User();
            return userRepository.findUserByEmail(username).get().getPwd();
        }
        //Else is phone number
        else if(phoneVal(username)) {
            com.volunteer.springbootmongo.models.data.User user = new com.volunteer.springbootmongo.models.data.User();
            return userRepository.findUserByPhonenumber(username).get().getPwd();
        }
        return null;
    }
}
