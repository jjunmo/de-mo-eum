package kr.co.promisemomo.module.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "promise")
public class Promise {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "promise_name")
    private String name;

    private Integer year;
    private Integer month;
    private Integer day;

    @Column(name = "start_time")
    private Integer startTime;

    @Column(name = "end_time")
    private Integer endTime;

    // 주최자의 회원 정보
    @ManyToOne
    @JoinColumn(name="member_id", referencedColumnName = "id")
    private Member member;

    // 참가자들
    // 캘린더에서 바로 참가자가 몇명인지 확인을 해야하는 경우?
//    @OneToMany(mappedBy = "promise")
//    private List<PromiseMember> members = new ArrayList<>();

}
