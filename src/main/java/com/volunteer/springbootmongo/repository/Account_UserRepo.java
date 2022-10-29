package com.volunteer.springbootmongo.repository;

import com.volunteer.springbootmongo.models.data.Account_User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Account_UserRepo extends MongoRepository<Account_User,String> {

}
