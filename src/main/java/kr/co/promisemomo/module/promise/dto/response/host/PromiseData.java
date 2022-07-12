package kr.co.promisemomo.module.promise.dto.response.host;

import kr.co.promisemomo.module.promise.entity.PromiseMember;
import lombok.Data;
import java.util.List;

@Data
public class PromiseData {

    private Long id;
    private String name;
    private Integer year;
    private Integer month;
    private Integer day;
    private String startTime;
    private String endTime;
    private String cancel;

    private List<PromiseMemberData> members;

}
