package com.volunteer.springbootmongo.models.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author "KhaPhan" on 12-Dec-22
 * @project volunteer-springboot-mongodb
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HDBankAccount {
    private String userID;
    private String accountName;
    private String accountNumber;
    private String phone;
    private String email;
    private String identityNumber;
    private Date dateOfBirth;
    private String address;
    private Date linkedDate = new Date();
    private Float balance;
}