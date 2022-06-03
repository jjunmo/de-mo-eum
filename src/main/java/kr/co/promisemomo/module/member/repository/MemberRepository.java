package kr.co.promisemomo.module.member.repository;

import kr.co.promisemomo.module.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByKakaoId(Long k_kakaoId);
}
