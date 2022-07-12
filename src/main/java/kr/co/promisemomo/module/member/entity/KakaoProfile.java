package kr.co.promisemomo.module.member.entity;

import kr.co.promisemomo.module.common.BaseTime;
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
public class KakaoProfile extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    //사용자 프로퍼티 추가

    @Column(name = "kpKakaoId", unique = true)
    private Long kpKakaoId;

    @Column(name = "kpNickname" ,nullable = true)
    private String kpNickname;

    @Column(name = "kpProfile_image_url",nullable = true)
    private String kpProfile_image_url;

    @Column(name = "kpThumbnail_image_url",nullable = true)
    private String kpThumbnail_image_url;

    @Column(name = "kpEmail")
    private String kpEmail;
}
