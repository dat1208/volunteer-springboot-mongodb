package com.volunteer.springbootmongo.Donation.Repository;

import com.volunteer.springbootmongo.Donation.Model.DonateHistory;
import com.volunteer.springbootmongo.Donation.Model.DonateModel;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author "KhaPhan" on 16-Dec-22
 * @project volunteer-springboot-mongodb
 */
public interface DonateHistoryRepository extends MongoRepository<DonateHistory,String> {
}
