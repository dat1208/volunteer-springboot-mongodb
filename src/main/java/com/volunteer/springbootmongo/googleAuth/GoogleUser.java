package com.volunteer.springbootmongo.googleAuth;

import lombok.*;

/**
 * @author "KhaPhan" on 05-Nov-22
 * @project volunteer-springboot-mongodb
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class GoogleUser {
    private String email;
    private String given_name;
    private String id;
    private String locale;
    private String name;
    private String picture;
    private boolean verified_email;
}
