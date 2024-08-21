package kr.co.promisemomo.module.promise.dto.response.participant;


import lombok.Data;

import java.util.List;
@Data
public class PromiseWaitData {

    private Long id;
    private String name;
    private Integer year;
    private Integer month;
    private Integer day;
    private String startTime;
    private String endTime;

    private Long promiseMemberId;
}
