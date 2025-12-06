package com.studentexpensetracker.studentexpensetracker.service;

import com.studentexpensetracker.studentexpensetracker.entity.ProfileEntity;
import com.studentexpensetracker.studentexpensetracker.exception.OAuth2ProviderException;
import com.studentexpensetracker.studentexpensetracker.repo.ProfileRepository;
import com.studentexpensetracker.studentexpensetracker.security.OAuth2UserInfo.OAuth2UserInfo;
import com.studentexpensetracker.studentexpensetracker.security.OAuth2UserInfo.OAuth2UserInfoFactory;
import com.studentexpensetracker.studentexpensetracker.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final ProfileRepository profileRepository;

    private final OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String email = oAuth2User.getAttribute("email");

        if (email == null || email.isBlank()) {
            if (userRequest.getClientRegistration().getRegistrationId().equalsIgnoreCase("github")) {
                throw new OAuth2ProviderException("No Email Found. Make sure to set your email public in GitHub settings.");
            }
        }

        return oAuth2User;
    }
}
