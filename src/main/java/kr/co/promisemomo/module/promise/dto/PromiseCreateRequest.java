package kr.co.promisemomo.module.promise.dto;

import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.promise.entity.Promise;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PromiseCreateRequest {
    private String name;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer startTime;
    private Integer endTime;

    // List 초대 회원
    private List<Member> member;

    public Promise dtoToEntity(Member member) {
        Promise promise = new Promise();
        promise.setName(name);
        promise.setYear(year);
        promise.setMonth(month);
        promise.setDay(day);
        promise.setStartTime(startTime);
        promise.setEndTime(endTime);
        promise.setMember(member);
        return promise;
    }

}
