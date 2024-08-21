package com.akyc.oauth2.google.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/oauth")
public class Oauth2Controller {
    @GetMapping("/loginInfo")
    public ResponseEntity<Object> getJson(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        return ResponseEntity.ok().body(attributes.toString());
    }
}
