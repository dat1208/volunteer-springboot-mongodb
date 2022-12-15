package com.volunteer.springbootmongo.repository;

import com.volunteer.springbootmongo.models.data.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
public interface UserRepository
        extends MongoRepository<User,String> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByPhonenumber(String phone);
}
