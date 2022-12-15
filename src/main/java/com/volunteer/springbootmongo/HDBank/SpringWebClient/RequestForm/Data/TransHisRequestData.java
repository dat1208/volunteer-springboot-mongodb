package com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "KhaPhan" on 14-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransHisRequestData {
    private String  acctNo;
    private String  fromDate;
    private String  toDate;
}
