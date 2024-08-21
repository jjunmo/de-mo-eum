package kr.aiapp.logservice.repository;

import kr.aiapp.logservice.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log,Long> {
}
