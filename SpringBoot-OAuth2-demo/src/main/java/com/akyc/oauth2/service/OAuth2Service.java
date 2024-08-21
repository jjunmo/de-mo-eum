package com.akyc.oauth2.service;

import com.akyc.oauth2.common.OAuth2UserInfo;
import com.akyc.oauth2.entity.Users;
import com.akyc.oauth2.repository.UsersRepository;
import com.akyc.oauth2.util.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService {

    private final UsersRepository userRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

//        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2Service = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 로그인을 수행한 서비스의 이름

        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // PK가 되는 정보

        Map<String, Object> attributes = oAuth2User.getAttributes(); // 사용자가 가지고 있는 정보

        OAuth2UserInfo oAuth2UserInfo = OAuthAttributes.extract(registrationId, attributes);
        oAuth2UserInfo.setProvider(registrationId);

        updateOrSaveUser(oAuth2UserInfo);

        Map<String, Object> customAttribute =
                getCustomAttribute(registrationId, userNameAttributeName, attributes, oAuth2UserInfo);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                customAttribute,
                userNameAttributeName);
    }

    public Map<String, Object> getCustomAttribute(String registrationId,
                                                  String userNameAttributeName,
                                                  Map<String, Object> attributes,
                                                  OAuth2UserInfo oAuth2UserInfo) {
        Map<String, Object> customAttribute = new ConcurrentHashMap<>();

        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("provider", registrationId);
        customAttribute.put("name", oAuth2UserInfo.getUsername());
        customAttribute.put("email", oAuth2UserInfo.getEmail());

        return customAttribute;
    }

    public Users updateOrSaveUser(OAuth2UserInfo userInfo) {
        Users user = userRepository
                .findUserByEmailAndProvider(userInfo.getEmail(), userInfo.getProvider())
                .map(value -> value.updateUser(userInfo.getUsername(), userInfo.getEmail()))
                .orElse(userInfo.toEntity());

        return userRepository.save(user);
    }
}
