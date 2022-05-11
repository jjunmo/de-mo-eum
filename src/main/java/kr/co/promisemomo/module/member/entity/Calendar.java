package kr.co.promisemomo.module.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Calendar")
@IdClass(CalendarPK.class)
public class Calendar {

    @Id
    private int year;

    @Id
    private int month;

    @Id
    private int day;


    @MapsId
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name = "year",referencedColumnName = "year"),
            @JoinColumn(name = "month",referencedColumnName = "month"),
            @JoinColumn(name = "day",referencedColumnName = "day")
    })
    List<CalendarData> calendarDataList;
}
