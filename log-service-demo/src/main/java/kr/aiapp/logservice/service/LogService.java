package kr.aiapp.logservice.service;

import kr.aiapp.logservice.entity.Log;
import kr.aiapp.logservice.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;


@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Transactional
    public void log(String traceId,
                    String spanId,
                    String parentSpanId,
                    String serviceName,
                    String logLevel,
                    String message,
                    String endpoint,
                    Integer duration) {
        Log log = new Log();
        log.setTraceId(traceId);
        log.setSpanId(spanId);
        log.setParentSpanId(parentSpanId);
        log.setServiceName(serviceName);
        log.setLogLevel(logLevel);
        log.setLogMessage(message);
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));
        log.setEndpoint(endpoint);
        log.setDuration(duration);

        logRepository.save(log);
    }
}
