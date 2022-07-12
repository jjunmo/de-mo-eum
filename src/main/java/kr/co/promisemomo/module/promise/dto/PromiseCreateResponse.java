package kr.co.promisemomo.module.promise.dto;

import kr.co.promisemomo.module.member.entity.Member;
import lombok.Data;

@Data
public class PromiseCreateResponse {
    private Long id;
    private String name;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer startTime;
    private Integer endTime;
    private Member member;
}
