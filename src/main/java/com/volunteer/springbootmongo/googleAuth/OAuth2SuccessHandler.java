package com.volunteer.springbootmongo.googleAuth;

import com.volunteer.springbootmongo.config.JwtTokenUtil;
import com.volunteer.springbootmongo.models.data.User;
import org.springframework.beans.factory.annotation.Autowired;
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
        logger.info("azp" + oAuth2User.getAttribute("tokenValue"));
    }
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        getUserInAuthentication(authentication);
        // storeUserToDB(User);
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal() ;
        String token = jwtTokenUtil.generateToken(oAuth2User.getAttribute("name").toString());
        logger.info("This is token" + token);
        // getAccesstoken (googleapi);
        // refreshToken
        // redirect controller
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
