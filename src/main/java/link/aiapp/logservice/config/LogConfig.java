package link.aiapp.logservice.config;

import link.aiapp.logservice.Interceptor.LogInterceptor;
import link.aiapp.logservice.repository.LogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class LogConfig {

    @Bean(name = "LogInterceptor")
    public LogInterceptor logInterceptor(LogRepository logRepository, Tracer tracer, @Value("${spring.application.name:unknown}") String applicationName) {
        // LogInterceptor를 빈으로 정의하여 applicationName을 주입받음
        System.out.println("LogRepository: " + logRepository); // 로그로 빈 확인
        System.out.println("Tracer: " + tracer); // 로그로 빈 확인
        System.out.println("Application Name: " + applicationName);
        return new LogInterceptor(logRepository, tracer, applicationName);
    }
//
//    private final ApplicationContext applicationContext;
//    private final String applicationName;
//
//    public LogConfig(ApplicationContext applicationContext, @Value("${spring.application.name:unknown}") String applicationName) {
//        this.applicationContext = applicationContext;
//        this.applicationName = applicationName;
//    }
//
//    @Bean(name = "LogInterceptor")
//    public LogInterceptor logInterceptor(LogRepository logRepository, Tracer tracer) {
//        // LogInterceptor를 빈으로 정의하여 applicationName을 주입받음
//        System.out.println("LogRepository: " + logRepository); // 로그로 빈 확인
//        System.out.println("Tracer: " + tracer); // 로그로 빈 확인
//        System.out.println("Application Name: " + applicationName);
//        return new LogInterceptor(logRepository, tracer, applicationName);
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // 동적으로 생성된 LogInterceptor 빈 이름으로 인터셉터 등록
//        registry.addInterceptor( (LogInterceptor) applicationContext.getBean("LogInterceptor"))
//                .order(Integer.MAX_VALUE)
//                .addPathPatterns("/**");
//    }
}