package com.volunteer.springbootmongo.service.user;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.volunteer.springbootmongo.models.LoginForm;
import com.volunteer.springbootmongo.models.RegisterForm;
import com.volunteer.springbootmongo.models.UpdateForm;
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
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.List;

@AllArgsConstructor
@Service
@Repository
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Regex validate email
    public boolean emailVal(String userName){
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }

    //Regex validate password
    //Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
    public boolean passwordVal(String password){
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    //Regex validate phone number
    public boolean phoneVal(String phonenumber){
        String regex = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phonenumber);
        return matcher.matches();
    }
    public boolean usernameVal(String username){
        if(emailVal(username) || phoneVal(username))
            return true;
        else return false;
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

             if(BCrypt.checkpw(loginForm.getPassword(),user.getPwd()))
               return true;
             else return false;
        }
        //Else is phone number
        else if(phoneVal(loginForm.getUsername())) {
            User user = new User();
            userRepository.findUserByPhonenumber(loginForm.getUsername()).ifPresent(u -> {
                user.setPwd(u.getPwd());
            });

            if(BCrypt.checkpw(loginForm.getPassword(),user.getPwd()))
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

            if(BCrypt.checkpw(loginForm.getPassword(),user.getPwd()))
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

            if(BCrypt.checkpw(loginForm.getPassword(),user.getPwd()))
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

    public String Gender(boolean gender){
        if(gender == true)
            return "male";
        else return "female";
    }
    public ResponseUser getUserByUsername(String userName) throws Exception{
        //Validate Email
        if(emailVal(userName)){
            User user = new User();
            userRepository.findUserByEmail(userName).ifPresent(u -> {
                user.set_id(u.get_id());
                user.setPwd(u.getPwd());
                user.setFirstname(u.getFirstname());
                user.setEmail(u.getEmail());
                user.setLastname(u.getLastname());
                user.setPhonenumber(u.getPhonenumber());
                user.setGender(u.getGender());
                user.setAddress(u.getAddress());
                user.setAvatar(u.getAvatar());
                user.setCover(u.getCover());
                user.setBirth(u.getBirth());
            });
            ResponseUser responseUser = new ResponseUser(user.get_id().toString(),user.getFirstname(),user.getLastname(), user.getBirth(),user.getEmail(),
                    user.getPhonenumber(), user.getAvatar(), user.getCover(),Gender(user.getGender()),user.getAddress());
            return new ResponseUser(responseUser);
        }
        //Else is phone number
        else if(phoneVal(userName)) {
            User user = new User();

            userRepository.findUserByPhonenumber(userName).ifPresent(u -> {
                user.set_id(u.get_id());
                user.setPwd(u.getPwd());
                user.setFirstname(u.getFirstname());
                user.setEmail(u.getEmail());
                user.setLastname(u.getLastname());
                user.setPhonenumber(u.getPhonenumber());
                user.setGender(u.getGender());
                user.setAddress(u.getAddress());
                user.setAvatar(u.getAvatar());
                user.setCover(u.getCover());
                user.setBirth(u.getBirth());
            });
            ResponseUser responseUser = new ResponseUser(user.get_id().toString(),user.getFirstname(),user.getLastname(), user.getBirth(),user.getEmail(),
                    user.getPhonenumber(), user.getAvatar(), user.getCover(),Gender(user.getGender()),user.getAddress());
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
                    registerForm.getEmail(), registerForm.getPhonenumber(), passwordEncoder.encode(registerForm.getPassword()));
           insertUser(user,registerForm.getType());
           return new ResponseObject((HttpStatus.CREATED.toString()),user);
       }
    }

    //Insert user with two para User,Type : Type is {single, organization}
    public void insertUser(User user, String type){
        userRepository.insert(user);
        Account account = new Account(user.get_id().toString(),type,null);
        accountRepository.insert(account);
        Account_User account_user = new Account_User(account.get_id().toString(),false);
        account_userRepo.insert(account_user);
        Permission_Type permission_type = permission_typeRepo.findPermission_TypeByName("User").get();
        Permission permission = new Permission(account_user.get_id().toString(),user.get_id().toString(),permission_type.get_id().toString());
        permissionRepository.insert(permission);
    }

    //Check username by phone or email if exist return true else false
    public boolean checkUsername(String username){
        if(emailVal(username)){
            return userRepository.findUserByEmail(username).isPresent();

        }
        //Else is phone number
        else if(phoneVal(username)) {
            return userRepository.findUserByPhonenumber(username).isPresent();
        }
        return false;
    }

    //Get password by username if not exist return null
    public String getPassword(String username){
        if(emailVal(username)){
            return userRepository.findUserByEmail(username).get().getPwd();
        }
        //Else is phone number
        else if(phoneVal(username)) {
            return userRepository.findUserByPhonenumber(username).get().getPwd();
        }
        return null;
    }

    public ResponseObject update(UpdateForm updateForm) {
        boolean gender = updateForm.getGender().equals("male") ? true : false;

        if (emailVal(updateForm.getEmail()) && phoneVal(updateForm.getPhonenumber())){
            String oldMail = updateForm.getOldmail();
            userRepository.findUserByEmail(oldMail).ifPresent(u -> {
                u.setAvatar(getAvatar(updateForm.getEmail()));
                u.setCover(getCover(updateForm.getEmail()));
                u.setPhonenumber(updateForm.getPhonenumber());
                u.setFirstname(updateForm.getFirstname());
                u.setLastname(updateForm.getLastname());
                u.setEmail(updateForm.getEmail());
                u.setBirth(updateForm.getBirth());
                u.setGender(gender);
                userRepository.save(u);
            });
            return new ResponseObject(HttpStatus.CREATED.toString(), userRepository.findUserByEmail(updateForm.getEmail()));
        } else return new ResponseObject(HttpStatus.NOT_ACCEPTABLE.toString(), "wrong_format");
    }

    public void insertPost(String id, String username){
        if(emailVal(username)){
            userRepository.findUserByEmail(username).ifPresent(u -> {
                if(u.getPosts() != null){
                    var listPost = new java.util.ArrayList<>(u.getPosts().stream().toList());
                    listPost.add(id);
                    u.setPosts(listPost);
                }
                else {
                    u.setPosts(Collections.singletonList(id));
                }
                userRepository.save(u);
            });
        }else{
            userRepository.findUserByPhonenumber(username).ifPresent(u -> {
                if(u.getPosts() != null){
                    var listPost = new java.util.ArrayList<>(u.getPosts().stream().toList());
                    listPost.add(id);
                    u.setPosts(listPost);
                }
                else {
                    u.setPosts(Collections.singletonList(id));
                }
                userRepository.save(u);
            });
        }
    }
    public String getAvatar(String username){
        String avt = "avatar.png";
        String users_folder = "users";
        String blob = users_folder+"/"+username+"/"+avt;
        String url = "https://storage.googleapis.com/volunteer-app-c93c9.appspot.com/"+blob;
        return url;
    }

    public String getCover(String username){
        String cover = "cover.png";
        String users_folder = "users";
        String blob = users_folder+"/"+username+"/"+cover;
        String url = "https://storage.googleapis.com/volunteer-app-c93c9.appspot.com/"+blob;
        return url;
    }

    public ResponseObject getStories() {
        List<User> userList = userRepository.findAll().stream().toList();
        List<String> stories = new ArrayList<>();
        for (User user:
             userList) {
            stories.add(getAvatar(user.getEmail()));
        }
        return new ResponseObject(HttpStatus.OK.toString(),stories);
    }
}
