package com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Responses;

import com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Data.HDBankTuitionListResponseData;
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
public class TuitionListResponse {
    private Response response;
    private HDBankTuitionListResponseData data;
}
