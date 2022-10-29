package com.volunteer.springbootmongo.repository;

import com.volunteer.springbootmongo.models.data.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PermissionRepository extends MongoRepository<Permission,String> {


}
