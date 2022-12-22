package com.volunteer.springbootmongo.Donation.Interface;

import com.volunteer.springbootmongo.Donation.Request.RequireDonateRequest;
import com.volunteer.springbootmongo.models.response.ResponseObject;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author "KhaPhan" on 16-Dec-22
 * @project volunteer-springboot-mongodb
 */
public interface DonateInterface {

    public ResponseObject requireDonate(@RequestBody RequireDonateRequest donateRequest);

    public ResponseObject donation_require(RequireDonateRequest donateRequest);
}
