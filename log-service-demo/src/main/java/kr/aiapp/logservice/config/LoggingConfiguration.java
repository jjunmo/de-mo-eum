package kr.aiapp.logservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class LoggingConfiguration implements WebMvcConfigurer {

    @Autowired
    private CommonHttpRequestInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor);
        System.out.println("LoggingInterceptor 등록 완료"); // 디버깅용 메시지
    }
}
