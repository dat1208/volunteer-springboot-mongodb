package com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm;

import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.BalanceRequestData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "KhaPhan" on 15-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceRequest {
    private Request request;
    private BalanceRequestData data;
}
