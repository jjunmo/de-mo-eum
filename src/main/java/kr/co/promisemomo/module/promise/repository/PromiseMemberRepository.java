package kr.co.promisemomo.module.promise.repository;

import kr.co.promisemomo.module.promise.entity.PARTICIPATE;
import kr.co.promisemomo.module.promise.entity.PromiseMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromiseMemberRepository extends JpaRepository<PromiseMember, Long> {
    List<PromiseMember> findByMember_Id(Long member_id);
    List<PromiseMember> findByPromise_Id(Long promise_id);

    List<PromiseMember> findByParticipateAndPromise_Id(PARTICIPATE participate, Long promise_id);
    List<PromiseMember> findByParticipateAndMember_Id(PARTICIPATE participate, Long member_id);

}
