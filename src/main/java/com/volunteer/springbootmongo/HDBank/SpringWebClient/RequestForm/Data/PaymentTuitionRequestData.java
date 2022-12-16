package com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "KhaPhan" on 14-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTuitionRequestData {
    private String sdId;
    private String amount;
    private String description;
}
