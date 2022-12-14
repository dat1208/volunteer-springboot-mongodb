package com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Response;

import lombok.*;

import java.util.Date;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class Response {
       private String responseId;
       private String responseCode;
       private String responseMessage;
       private String responseTime;
}