package kr.co.promisemomo.module.member.repository;

import kr.co.promisemomo.module.member.entity.KakaoProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KakaoProfileRepository extends JpaRepository<KakaoProfile, Long> {
    List<KakaoProfile> findBykpKakaoId(Long kpKakaoId);
}
