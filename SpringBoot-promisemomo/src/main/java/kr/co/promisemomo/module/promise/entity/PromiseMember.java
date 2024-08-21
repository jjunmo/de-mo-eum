package kr.co.promisemomo.module.promise.entity;

import kr.co.promisemomo.module.common.BaseTime;
import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.promise.dto.response.participant.PromiseWaitData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name = "promise_member")
public class PromiseMember extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="promise_id",referencedColumnName = "id")
    private Promise promise;

    @Enumerated(EnumType.STRING)
    private PARTICIPATE participate;

    @ColumnDefault("'N'")
    private String cancel;

    // 주최자도 데이터 Insert 해야함
    // 참가자의 회원
    @ManyToOne
    @JoinColumn(name="member_id",referencedColumnName = "id")
    private Member member;


    public PromiseWaitData entityToDto(){
        PromiseWaitData promiseWaitData = new PromiseWaitData();
        Promise promise = this.getPromise();
        promiseWaitData.setName(promise.getName());
        promiseWaitData.setDay(promise.getDay());
        promiseWaitData.setMonth(promise.getMonth());
        promiseWaitData.setEndTime(promise.getEndTime());
        promiseWaitData.setId(promise.getId());
        promiseWaitData.setYear(promise.getYear());
        promiseWaitData.setStartTime(promise.getStartTime());
        promiseWaitData.setPromiseMemberId(this.getId());
        return promiseWaitData;
    }
}
