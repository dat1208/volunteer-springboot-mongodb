package com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Response;

import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Data.HDBankTransHisResponseData;
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
public class TransHisResponse {
    private Response response;
    private HDBankTransHisResponseData data;
}
