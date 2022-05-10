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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long idx;

    private Long kakaoId;

    private String nickname;

    private String profile_image_url;

    private String thumbnail_image_url;

    @JoinColumn(name = "")
    private KakaoProfile kakaoProfile;


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

}