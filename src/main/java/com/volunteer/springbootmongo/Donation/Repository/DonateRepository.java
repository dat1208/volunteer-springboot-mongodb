package com.volunteer.springbootmongo.Donation.Repository;

import com.volunteer.springbootmongo.Donation.Model.DonateModel;
import com.volunteer.springbootmongo.models.data.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author "KhaPhan" on 16-Dec-22
 * @project volunteer-springboot-mongodb
 */
public interface DonateRepository extends MongoRepository<DonateModel,String>{
    Optional<DonateModel> findDonateModelBy_id(ObjectId _id);
}
