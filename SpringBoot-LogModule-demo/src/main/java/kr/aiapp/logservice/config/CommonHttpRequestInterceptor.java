package kr.aiapp.logservice.config;

import kr.aiapp.logservice.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CommonHttpRequestInterceptor implements HandlerInterceptor {

    @Autowired
    private LogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("LoggingInterceptor 실행 중"); // 디버깅용 메시지
        String traceId = request.getHeader("X-B3-TraceId");
        String spanId = request.getHeader("X-B3-SpanId");
        String parentSpanId = request.getHeader("X-B3-ParentSpanId");

        logService.log(
                traceId,
                spanId,
                parentSpanId,
                request.getHeader("X-Service-Name"),
                "INFO",
                "Processing request: " + request.getRequestURI(),
                request.getRequestURI(),
                0
        );

        return true;
    }
}
