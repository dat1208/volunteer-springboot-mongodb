package com.volunteer.springbootmongo.Donation.Controller;

import com.volunteer.springbootmongo.Donation.Request.RequireDonateRequest;
import com.volunteer.springbootmongo.HDBank.Service.HDBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author "KhaPhan" on 15-Dec-22
 * @project volunteer-springboot-mongodb
 */
@RestController
@RequestMapping("/api/donation")
public class DonateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DonateController.class);

    @PostMapping("/require-donate")
    public ResponseEntity<?> requireDonate(@RequestBody RequireDonateRequest donateRequest) {
        LOGGER.info(donateRequest.toString());
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
