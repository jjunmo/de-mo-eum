package kr.co.promisemomo.module.member.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
    private Integer id;

    @Column(name = "promise_name")
    private String name;

    //하나의 멤버당 여러개의 약속
    @ManyToOne
    @JoinColumn(name="member_id",referencedColumnName = "id")
    private Member member;

    //하나의 약속에 여러명의 멤버
    @OneToMany(mappedBy = "promise")
    private List<Member> members=new ArrayList<>();
}
