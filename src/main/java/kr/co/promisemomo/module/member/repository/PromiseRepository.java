package kr.co.promisemomo.module.member.repository;

import kr.co.promisemomo.module.member.entity.Member;
import kr.co.promisemomo.module.member.entity.Promise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PromiseRepository extends JpaRepository<Promise, Long> {
    List<Promise> findByIdIn(List<Long> id);
    List<Promise> findByYearAndMonthAndMember_Id(int year, int month, Long member_id);
    // Select *
    //   From Promise
    //  Where member_id = #member_id
    //    And year = #year
    //    And month = #month
}
