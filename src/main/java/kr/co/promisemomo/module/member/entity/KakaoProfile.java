package kr.co.promisemomo.module.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="kakaoProfile")
public class KakaoProfile {

    @Id
    @GeneratedValue
    private Long id;

    //사용자 프로퍼티 추가

    @Column(name = "k_kakaoId")
    private Long k_kakaoId;

    @Column(name = "k_nickname")
    private String k_nickname;

    @Column(name = "k_profile_image_url")
    private String k_profile_image_url;

    @Column(name = "k_thumbnail_image_url")
    private String k_thumbnail_image_url;

}
