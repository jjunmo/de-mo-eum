package kr.co.promisemomo.module.member.entity;

import kr.co.promisemomo.module.member.Role.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Member")
public class Member {

    @Id
    @GeneratedValue
    private Long idx;

    @Column(name = "kakaoId")
    private Long kakaoId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile_image_url")
    private String profile_image_url;

    @Column(name = "thumbnail_image_url")
    private String thumbnail_image_url;

    @OneToOne
    @JoinColumn(name = "kakaoProfile", referencedColumnName = "k_kakaoId")
    private KakaoProfile kakaoProfile;

    @ManyToOne
    @JoinColumn(name = "promises", referencedColumnName = "promisename")
    private List<Promise> promises;


    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column(name = "create_date")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime updateDate;


    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    public Long getKakaoId() {
        return kakaoProfile.getK_kakaoId();
    }

    public String getNickname() {
        return kakaoProfile.getK_nickname();
    }

    public String getProfile_image_url() {
        return kakaoProfile.getK_profile_image_url();
    }

    public String getThumbnail_image_url() {
        return kakaoProfile.getK_thumbnail_image_url();
    }
}