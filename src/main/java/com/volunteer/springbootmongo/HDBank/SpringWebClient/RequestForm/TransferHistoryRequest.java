package com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "KhaPhan" on 15-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferHistoryRequest {
    private Request request;
    private TransferHistoryRequest data;
}
