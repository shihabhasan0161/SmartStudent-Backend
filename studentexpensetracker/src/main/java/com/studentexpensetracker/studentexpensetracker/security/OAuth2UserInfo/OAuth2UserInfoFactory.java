package com.studentexpensetracker.studentexpensetracker.security.OAuth2UserInfo;

import com.studentexpensetracker.studentexpensetracker.exception.OAuth2ProviderException;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String provider, Map<String, Object> attributes) throws OAuth2ProviderException {
        return switch (provider) {
            case "google" -> new GoogleOAuth2UserInfo(attributes);
//            case "github" -> new GithubOAuth2UserInfo(attributes, null);
            default -> throw new OAuth2ProviderException("OAuth2 provider not found");
        };
    }
}
