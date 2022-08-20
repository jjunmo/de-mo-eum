package kr.co.promisemomo.module.promise.entity;

import kr.co.promisemomo.module.common.BaseTime;
import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.promise.dto.response.PromiseCreateResponse;
import kr.co.promisemomo.module.promise.dto.response.host.PromiseData;
import kr.co.promisemomo.module.promise.dto.response.host.PromiseMemberData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name = "promise")
public class Promise extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "promise_name")
    @Comment("이름")
    private String name;

    @Comment("년도")
    private Integer year;
    private Integer month;
    private Integer day;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @ColumnDefault("'N'")
    private String cancel;

    // 주최자의 회원 정보
    @ManyToOne
    @JoinColumn(name="member_id", referencedColumnName = "id")
    private Member member;

    // 참가자들
    // 캘린더에서 바로 참가자가 몇명인지 확인을 해야하는 경우?
//    @OneToMany(mappedBy = "promise")
//    private List<PromiseMember> members = new ArrayList<>();

    public PromiseCreateResponse entityToDto (List<PromiseMember> promiseMembers) {
        PromiseCreateResponse promiseCreateResponse = new PromiseCreateResponse();
        promiseCreateResponse.setPromiseMembers(promiseMembers);
        promiseCreateResponse.setDay(this.day);
        promiseCreateResponse.setEndTime(this.endTime);
        promiseCreateResponse.setId(this.id);
        promiseCreateResponse.setMonth(this.month);
        promiseCreateResponse.setName(this.name);
        promiseCreateResponse.setStartTime(this.startTime);
        promiseCreateResponse.setYear(this.year);
        promiseCreateResponse.setMember(this.member);
        return promiseCreateResponse;
    }

    public PromiseData entityToHostDto (List<PromiseMember> promiseMemberList) {
        PromiseData promiseData = new PromiseData();
        promiseData.setDay(this.day);
        promiseData.setEndTime(this.endTime);
        promiseData.setId(this.id);
        promiseData.setMonth(this.month);
        promiseData.setName(this.name);
        promiseData.setStartTime(this.startTime);
        promiseData.setYear(this.year);

        List<PromiseMemberData> promiseMemberDataList = new ArrayList<>();

        // 참가자 정보
        promiseMemberList.forEach(
                promiseMember -> {
                    PromiseMemberData promiseMemberData = new PromiseMemberData();
                    promiseMemberData.setMemberId(promiseMember.getMember().getId());
                    promiseMemberData.setCancel(promiseMember.getCancel());
                    promiseMemberData.setName(promiseMember.getMember().getNickname());
                    promiseMemberData.setParticipate(promiseMember.getParticipate());
                    promiseMemberDataList.add(promiseMemberData);
                }
        );

        promiseData.setMembers(promiseMemberDataList);

        return promiseData;
    }

}
