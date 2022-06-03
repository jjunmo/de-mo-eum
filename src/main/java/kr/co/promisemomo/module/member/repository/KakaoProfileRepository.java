package kr.co.promisemomo.module.member.repository;

import kr.co.promisemomo.module.member.entity.KakaoProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoProfileRepository extends JpaRepository<KakaoProfile, Long> {
    boolean findByK_kakaoId(Long k_kakaoId);
}
