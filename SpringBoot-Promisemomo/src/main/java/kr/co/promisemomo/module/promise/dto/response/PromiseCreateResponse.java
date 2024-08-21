package kr.co.promisemomo.module.promise.dto.response;

import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.promise.entity.PromiseMember;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PromiseCreateResponse {

    private Long id;
    private String name;
    private Integer year;
    private Integer month;
    private Integer day;
    private String startTime;
    private String endTime;

    private Member member;

    private List<PromiseMember> promiseMembers = new ArrayList<>();

}
