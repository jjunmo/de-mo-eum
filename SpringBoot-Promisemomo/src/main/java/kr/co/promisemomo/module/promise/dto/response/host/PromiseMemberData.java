package kr.co.promisemomo.module.promise.dto.response.host;

import kr.co.promisemomo.module.promise.entity.PARTICIPATE;
import kr.co.promisemomo.module.promise.entity.Promise;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
public class PromiseMemberData {
    private Long memberId;
    private String name;
    private PARTICIPATE participate;
    private String cancel;
}
