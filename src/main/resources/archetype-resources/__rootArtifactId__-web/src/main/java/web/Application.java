package ${package}.web;

import ${package}.web.config.RestTemplateErrorHandler;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@ComponentScan(basePackages = {"${package}.model", "${package}.dao",
    "${package}.core", "${package}.facade", "${package}.facade.api", "${package}.web"})
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean("restTemplateHttp")
  public RestTemplate restTemplate() {
    HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    httpRequestFactory.setConnectionRequestTimeout(500);
    httpRequestFactory.setConnectTimeout(5000);
    httpRequestFactory.setReadTimeout(10000);

    RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
    restTemplate.setErrorHandler(new RestTemplateErrorHandler());
    return restTemplate;
  }
}