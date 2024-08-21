package com.akyc.oauth2.common;

import com.akyc.oauth2.entity.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuth2UserInfo {
    private String username;
    private String provider;
    private String email;


    public Users toEntity() {
        return Users.builder()
                .username(this.username)
                .email(this.email)
                .provider(this.provider)
                .build();
    }
}
