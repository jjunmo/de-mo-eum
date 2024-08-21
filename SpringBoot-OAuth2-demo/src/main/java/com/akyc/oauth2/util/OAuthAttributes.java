package com.akyc.oauth2.util;

import com.akyc.oauth2.common.OAuth2UserInfo;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes{
    GOOGLE("google", (attribute) -> {
        OAuth2UserInfo oAuth2UserInfo = new OAuth2UserInfo();
        oAuth2UserInfo.setUsername((String)attribute.get("name"));
        oAuth2UserInfo.setEmail((String)attribute.get("email"));
        return oAuth2UserInfo;
    });

//    NAVER("naver", (attribute) -> {
//        UserProfile userProfile = new UserProfile();
//
//        Map<String, String> responseValue = (Map)attribute.get("response");
//
//        userProfile.setUserName(responseValue.get("name"));
//        userProfile.setEmail(responseValue.get("email"));
//
//        return userProfile;
//    }),
//
//    KAKAO("kakao", (attribute) -> {
//
//        Map<String, Object> account = (Map)attribute.get("kakao_account");
//        Map<String, String> profile = (Map)account.get("profile");
//
//        UserProfile userProfile = new UserProfile();
//        userProfile.setUserName(profile.get("nickname"));
//        userProfile.setEmail((String)account.get("email"));
//
//        return userProfile;
//    });

    private final String registrationId; // 로그인한 서비스(ex) google, naver..)
    private final Function<Map<String, Object>, OAuth2UserInfo> of; // 로그인한 사용자의 정보를 통하여 UserProfile을 가져옴

    OAuthAttributes(String registrationId, Function<Map<String, Object>, OAuth2UserInfo> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static OAuth2UserInfo extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(value -> registrationId.equals(value.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }


}
