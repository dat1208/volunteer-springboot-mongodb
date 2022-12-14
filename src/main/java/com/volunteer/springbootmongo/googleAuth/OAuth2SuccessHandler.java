package com.volunteer.springbootmongo.googleAuth;

import com.volunteer.springbootmongo.config.EncoderConfig;
import com.volunteer.springbootmongo.config.JwtTokenUtil;
import com.volunteer.springbootmongo.models.data.User;
import com.volunteer.springbootmongo.service.jwt.JwtUserDetailsService;
import com.volunteer.springbootmongo.service.user.UserService;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    private User getUserInAuthentication(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        User googleUser = new User();

        googleUser.setEmail(oAuth2User.getAttribute("email"));
        googleUser.setLastname(oAuth2User.getAttribute("name"));
        googleUser.setAvatar(oAuth2User.getAttribute("picture"));

        //TODO: CHỔ NÀY PASSWORD VÌ LOGIN BẰNG GOOGLE THÌ GOOGLE VALIDATE RỒI - RETURN VỀ USER INFO

        googleUser.setPwd(passwordEncoder.encode("123"));

        return  googleUser;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // storeUserToDB(User);
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        final String single = "single";

        userService.insertUser(getUserInAuthentication(authentication),single);
        UserDetails username = jwtUserDetailsService.loadUserByUsername(oAuth2User.getAttribute("email"));
        String token = jwtTokenUtil.generateToken(username.getUsername());

        logger.info("This is token" + token);
        // refreshToken
        // redirect controller
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
