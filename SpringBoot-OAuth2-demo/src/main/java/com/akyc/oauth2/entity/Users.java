package com.akyc.oauth2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
//@DynamicUpdate // Entity update시, 원하는 데이터만 update하기 위함
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username; // 로그인한 사용자의 이름

    @Column(name = "email", nullable = false)
    private String email; // 로그인한 사용자의 이메일

    @Column(name = "provider", nullable = false)
    private String provider; // 사용자가 로그인한 서비스(ex) google, naver..)

    @Column(name = "provider_Id")
    private String providerId; // 사용자가 로그인한 서비스 고유번호

    public Users updateUser(String username, String email) {
        this.username = username;
        this.email = email;

        return this;
    }

}
