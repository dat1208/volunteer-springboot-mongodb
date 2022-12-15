package com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm;

import com.volunteer.springbootmongo.HDBank.SpringWebClient.RequestForm.Data.RegisterData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author "KhaPhan" on 13-Dec-22
 * @project volunteer-springboot-mongodb
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private Request request;
    private RegisterData data;
}
