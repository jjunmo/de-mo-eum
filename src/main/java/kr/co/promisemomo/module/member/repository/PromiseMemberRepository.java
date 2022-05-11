package kr.co.promisemomo.module.member.repository;

import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.member.entity.PromiseMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromiseMemberRepository extends JpaRepository<PromiseMember, Long> {
    List<PromiseMember> findByMember_Id(Long member_id);
}
