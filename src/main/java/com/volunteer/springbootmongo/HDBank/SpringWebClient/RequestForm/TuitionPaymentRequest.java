package com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm;

import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.ChangePasswordRequestData;
import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.PaymentTuitionRequestData;
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
public class TuitionPaymentRequest {
    private Request request;
    private PaymentTuitionRequestData data;
}
