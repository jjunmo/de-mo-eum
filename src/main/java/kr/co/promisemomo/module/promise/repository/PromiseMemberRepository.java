package kr.co.promisemomo.module.promise.repository;

import kr.co.promisemomo.module.promise.entity.PromiseMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromiseMemberRepository extends JpaRepository<PromiseMember, Long> {
    List<PromiseMember> findByMember_Id(Long member_id);
}
