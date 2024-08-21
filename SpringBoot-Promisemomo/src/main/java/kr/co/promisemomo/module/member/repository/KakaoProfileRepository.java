package kr.co.promisemomo.module.member.repository;

import kr.co.promisemomo.module.member.entity.KakaoProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KakaoProfileRepository extends JpaRepository<KakaoProfile, Long> {
    Optional<KakaoProfile> findBykpKakaoId(Long kpKakaoId);
}
