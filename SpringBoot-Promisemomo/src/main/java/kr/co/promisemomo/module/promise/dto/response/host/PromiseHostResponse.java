package kr.co.promisemomo.module.promise.dto.response.host;

import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.promise.entity.Promise;
import kr.co.promisemomo.module.promise.entity.PromiseMember;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PromiseHostResponse {
    private Integer promiseCnt;
    private List<PromiseData> promiseLists; // PromiseData -> Promise
}
