package com.volunteer.springbootmongo.HDBank.SpringWebClient.ResponseForm.Data;

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
public class HDBankTuitionData {
    private String  description;
    private String fee;
}
