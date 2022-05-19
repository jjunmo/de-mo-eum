package kr.co.promisemomo.module.member.repository;

import kr.co.promisemomo.module.member.entity.KakaoProfile;
import kr.co.promisemomo.module.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KakaoProfileRepository extends JpaRepository<KakaoProfile, Long> {

}
