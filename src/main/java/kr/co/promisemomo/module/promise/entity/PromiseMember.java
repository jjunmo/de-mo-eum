package kr.co.promisemomo.module.promise.entity;

import kr.co.promisemomo.module.common.BaseTime;
import kr.co.promisemomo.module.member.entity.Member;
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
@Table(name = "promise_member")
public class PromiseMember extends BaseTime {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="promise_id",referencedColumnName = "id")
    private Promise promise;

    // 주최자도 데이터 Insert 해야함
    // 참가자의 회원
    @ManyToOne
    @JoinColumn(name="member_id",referencedColumnName = "id")
    private Member member;
    
}
