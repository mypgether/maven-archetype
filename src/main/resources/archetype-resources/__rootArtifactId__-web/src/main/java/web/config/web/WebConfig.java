package ${package}.web.config.web;

import ${package}.web.config.databind.FiledKeyModelAttributeMethodProcessor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web的配置，如果不使用mvc框架可以去除
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Bean
  protected FiledKeyModelAttributeMethodProcessor processor() {
    return new FiledKeyModelAttributeMethodProcessor(true);
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(processor());
  }
}