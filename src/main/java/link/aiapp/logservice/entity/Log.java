package link.aiapp.logservice.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "MBS_LOG", indexes = {
        @Index(name = "idx_trace_id", columnList = "TRACE_ID"),
        @Index(name = "idx_span_id", columnList = "SPAN_ID"),
        @Index(name = "idx_service_name", columnList = "SERVICE_NAME"),
        @Index(name = "idx_log_level", columnList = "LOG_LEVEL"),
        @Index(name = "idx_http_method", columnList = "HTTP_METHOD")})
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_ID", updatable = false, nullable = false)
    private Long logId;

    @Column(name = "TRACE_ID", nullable = false)
    private String traceId;

    @Column(name = "SPAN_ID", nullable = false)
    private String spanId;

    @Column(name = "PARENT_SPAN_ID")
    private String parentSpanId;

    @Column(name = "SERVICE_NAME", nullable = false, length = 100)
    private String serviceName;

    @Column(name = "LOG_LEVEL", length = 10)
    private String logLevel;

    @Column(name = "LOG_MESSAGE", columnDefinition = "TEXT")
    private String logMessage;

    @Column(name = "TIMESTAMP", nullable = false)
    private LocalDateTime localDateTime;

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "ENDPOINT")
    private String endpoint;


    @Column(name = "HTTP_METHOD", nullable = false, length = 10)
    private String httpMethod;

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }


    @PrePersist
    protected void onCreate() {
        this.localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getParentSpanId() {
        return parentSpanId;
    }

    public void setParentSpanId(String parentSpanId) {
        this.parentSpanId = parentSpanId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

}
