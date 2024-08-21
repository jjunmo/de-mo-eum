package kr.co.promisemomo.module.promise.dto.request;

import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.promise.entity.Promise;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class PromiseCreateRequest {
    private String name;
    private Integer year;
    private Integer month;
    private Integer day;
    private String startTime;
    private String endTime;

    // List 초대 회원
    private List<Long> promiseMember = new ArrayList<>();

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
