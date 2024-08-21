package link.aiapp.logservice.repository;


import link.aiapp.logservice.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log,Long> {
}
