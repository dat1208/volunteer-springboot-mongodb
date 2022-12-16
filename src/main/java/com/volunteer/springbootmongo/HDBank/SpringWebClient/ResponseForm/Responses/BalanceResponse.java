package com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Responses;

import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Data.HDBankBalanceResponseData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "KhaPhan" on 14-Dec-22
 * @project volunteer-springboot-mongodb
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BalanceResponse {
    private Response response;
    private HDBankBalanceResponseData data;
}