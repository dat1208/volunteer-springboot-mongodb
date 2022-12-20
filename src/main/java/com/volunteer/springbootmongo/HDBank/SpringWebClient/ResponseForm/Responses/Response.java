package com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Responses;

import lombok.*;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Response {
       private String responseId;
       private String responseCode;
       private String responseMessage;
       private String responseTime;
}