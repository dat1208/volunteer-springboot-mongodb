package com.volunteer.springbootmongo.googleAuth;

import com.volunteer.springbootmongo.models.data.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @author "KhaPhan" on 02-Nov-22
 * @project volunteer-springboot-mongodb
 */
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private void getUserInAuthentication(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal() ;
        logger.info("Username" + oAuth2User.getAttribute("name"));
        logger.info("Email" + oAuth2User.getAttribute("email"));
        logger.info("pic" + oAuth2User.getAttribute("picture"));
        logger.info("aud" + oAuth2User.getAttribute("aud"));
        logger.info("azp" + oAuth2User.getAttribute("azp"));
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info(authentication.getPrincipal());
        getUserInAuthentication(authentication);
        // storeUserToDB(User);
        // getAccesstoken (googleapi);
        // refreshToken
        // redirect controller
        String index = "1";
        switch (index) {
            case "12":
                break;

            default:

        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}