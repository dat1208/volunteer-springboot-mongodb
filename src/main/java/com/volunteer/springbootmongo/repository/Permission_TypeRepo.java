package com.volunteer.springbootmongo.repository;

import com.volunteer.springbootmongo.models.data.Permission_Type;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface Permission_TypeRepo extends MongoRepository<Permission_Type,String> {
    Optional<Permission_Type> findPermission_TypeByName(String name);
}
