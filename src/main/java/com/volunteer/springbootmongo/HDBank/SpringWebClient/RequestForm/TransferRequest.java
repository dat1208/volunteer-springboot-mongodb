package com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm;

import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.TransferRequestData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "KhaPhan" on 15-Dec-22
 * @project volunteer-springboot-mongodb
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferRequest {
    private Request request;
    private TransferRequestData data;
}
