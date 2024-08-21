package link.aiapp.logservice.annotation;

import link.aiapp.logservice.config.LogConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/*
RepositoryScan , EntityScan 문제로 미사용
 */
@Deprecated
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(LogConfig.class)
public @interface EnableLogInterceptor {
}
