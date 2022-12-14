package com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class Request {
    private String requestId = "UUID";
    private String requestTime = "YYYY-MM-DD HH24:MI:SS";
}
