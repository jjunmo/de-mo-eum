package kr.co.promisemomo.module.member.entity;

import kr.co.promisemomo.module.common.BaseTime;
import kr.co.promisemomo.module.member.role.RoleType;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Table(name = "member")
public class Member extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "kakaoId")
    private Long kakaoId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "profile_image_url")
    private String profile_image_url;

    @Column(name = "thumbnail_image_url")
    private String thumbnail_image_url;

    @ColumnDefault("'N'")
    private String deleteCheck;

    //카카오프로필정보
    @OneToOne
    @JoinColumn(name = "kakaoProfile", referencedColumnName = "id")
    private KakaoProfile kakaoProfile;

    // 하나의 약속에 여러명의 멤버
    // => 회원 한명이 약속 하나밖에 못만듬
//    @ManyToOne
//    @JoinColumn(name = "promise_id", referencedColumnName = "id")
//    private Promise promise;

    // 하나의 멤버당 여러개의 약속
//    @OneToMany(mappedBy ="member")
//    private List<Promise> promises = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RoleType role;

//    @Column(name = "create_date")
////    @DateTimeFormat(pattern = "yyyy-mm-dd")
//    private LocalDateTime createDate;
//
//    @Column(name = "update_date")
////    @DateTimeFormat(pattern = "yyyy-mm-dd")
//    private LocalDateTime updateDate;
//    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
//    public void createDate() {
//        this.createDate = LocalDateTime.now();
//    }


    public void settingKakaoProfile(KakaoProfile kakaoProfile) {
        this.kakaoProfile = kakaoProfile;
        this.kakaoId = kakaoProfile.getKpKakaoId();
        this.nickname = kakaoProfile.getKpNickname();
        this.email = kakaoProfile.getKpEmail();
        this.profile_image_url = kakaoProfile.getKpProfile_image_url();
        this.thumbnail_image_url = kakaoProfile.getKpThumbnail_image_url();
    }

}