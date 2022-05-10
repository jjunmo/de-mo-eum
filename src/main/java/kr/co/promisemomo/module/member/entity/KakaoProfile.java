package kr.co.promisemomo.module.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Member")
public class KakaoProfile {

    @Id
    @GeneratedValue
    private Long id;

    //사용자 프로퍼티 추가

    private Long k_kakaoId;

    private String k_nickname;

    private String k_profile_image_url;

    private String k_thumbnail_image_url;




}
