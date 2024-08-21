package link.aiapp.logservice.Interceptor;

import link.aiapp.logservice.entity.Log;
import link.aiapp.logservice.repository.LogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

public class LogInterceptor implements HandlerInterceptor {

    private final LogRepository logRepository;
    private final Tracer tracer;
    private final String applicationName;

    public LogInterceptor(LogRepository logRepository, Tracer tracer, @Value("${spring.application.name:unknown}") String applicationName) {
        this.logRepository = logRepository;
        this.tracer = tracer;
        this.applicationName = applicationName;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 요청 시작 시간을 기록 (밀리초 단위)
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 요청 완료 시간과 duration 계산
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();

        // 밀리초 단위를 초 단위로 변환
        int duration = (int) ((endTime - startTime) / 1000.0);

        Log log = new Log();

        // 현재 Span을 가져와서 로그 엔티티에 설정
        Span currentSpan = tracer.currentSpan();
        if (currentSpan != null) {
            log.setTraceId(currentSpan.context().traceId());
            log.setSpanId(currentSpan.context().spanId());
            log.setParentSpanId(currentSpan.context().parentId());
        }

        // 어플리케이션 이름을 서비스 이름으로 설정
        log.setServiceName(applicationName);

        // 추가적인 요청 정보를 로그에 설정
        log.setEndpoint(request.getRequestURI());
        log.setLogLevel("INFO");
        log.setLogMessage("Incoming request: " + request.getMethod() + " " + request.getRequestURI());
        log.setDuration(duration);

        // HTTP 메서드를 로그에 설정
        log.setHttpMethod(request.getMethod());

        // 로그 저장
        logRepository.save(log);
    }
}
