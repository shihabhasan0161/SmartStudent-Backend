package com.studentexpensetracker.studentexpensetracker.security;

import com.studentexpensetracker.studentexpensetracker.entity.ProfileEntity;
import com.studentexpensetracker.studentexpensetracker.repo.ProfileRepository;
import com.studentexpensetracker.studentexpensetracker.security.OAuth2UserInfo.OAuth2UserInfo;
import com.studentexpensetracker.studentexpensetracker.security.OAuth2UserInfo.OAuth2UserInfoFactory;
import com.studentexpensetracker.studentexpensetracker.util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.oauth2.success-redirect}")
    private String successRedirectUrl;

    private final ProfileRepository profileRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oauthToken.getPrincipal();

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                oauthToken.getAuthorizedClientRegistrationId(),
                oAuth2User.getAttributes()
        );

        String email = userInfo.getEmail();
        if (email == null) {
            throw new ServletException("Email not found from OAuth2 provider");
        }
        String name = userInfo.getName();
        if (name == null) {
            throw new ServletException("Name not found from OAuth2 provider");
        }

        ProfileEntity profile = profileRepository.findByEmail(email).orElseGet(() -> {
            ProfileEntity newProfile = new ProfileEntity();
            newProfile.setEmail(email);
            newProfile.setFullName(name);
            return profileRepository.save(newProfile);
        });

        // Get jwt token
        String token = jwtUtil.generateToken(profile.getEmail());

        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);

        // Redirect to frontend with token
        String redirectUrl = UriComponentsBuilder
                .fromUriString(successRedirectUrl)
                .queryParam("token", encodedToken)
                .build()
                .toUriString();
        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

}
