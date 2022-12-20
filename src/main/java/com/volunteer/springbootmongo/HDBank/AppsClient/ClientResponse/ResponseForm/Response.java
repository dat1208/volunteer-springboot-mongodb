package com.volunteer.springbootmongo.HDBank.AppsClient.ClientResponse.ResponseForm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * @author "KhaPhan" on 15-Dec-22
 * @project volunteer-springboot-mongodb
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response {
    private UUID requestID = UUID.randomUUID();
    private Status status;
    private Message message;
    private Date time = new Date();

    public Response(Status status, Message message) {
        this.status = status;
        this.message = message;
    }
}
