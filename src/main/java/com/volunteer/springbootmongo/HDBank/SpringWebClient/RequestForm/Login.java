package com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class Login{
    private String credential;
    private String key;
}
