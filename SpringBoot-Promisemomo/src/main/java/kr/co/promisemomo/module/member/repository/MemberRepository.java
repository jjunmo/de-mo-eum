package kr.co.promisemomo.module.member.repository;

import kr.co.promisemomo.module.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByKakaoIdAndDeleteCheck(Long kakaoId, String deleteCheck);
    List<Member> findByDeleteCheck(String deleteCheck);
    Optional<Member> findByIdAndDeleteCheck(Long id, String deleteCheck);


}
