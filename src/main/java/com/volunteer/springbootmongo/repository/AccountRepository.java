package com.volunteer.springbootmongo.repository;

import com.volunteer.springbootmongo.models.data.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account,String> {
}
