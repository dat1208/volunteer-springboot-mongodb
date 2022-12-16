package com.volunteer.springbootmongo.Donation.Repository;

import com.volunteer.springbootmongo.Donation.Model.DonateModel;
import com.volunteer.springbootmongo.models.data.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author "KhaPhan" on 16-Dec-22
 * @project volunteer-springboot-mongodb
 */
public interface DonateRepository extends MongoRepository<DonateModel,String>{
}
