package kr.co.promisemomo.module.member.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
public class Promise {

    @Id
    private Integer idx;

    @Column(name = "promisename")
    private String name;


    @OneToMany

    private Member members;
}
