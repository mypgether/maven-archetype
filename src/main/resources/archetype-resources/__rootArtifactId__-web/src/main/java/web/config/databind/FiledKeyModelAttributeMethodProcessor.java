package ${package}.web.config.databind;

import javax.servlet.ServletRequest;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

/**
 * mvc框架的databind，如果不使用mvc框架可以去除
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
public class FiledKeyModelAttributeMethodProcessor extends
    ServletModelAttributeMethodProcessor implements ApplicationContextAware {

  ApplicationContext applicationContext;

  public FiledKeyModelAttributeMethodProcessor(boolean annotationNotRequired) {
    super(annotationNotRequired);
  }

  @Override
  protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
    FiledKeyRequestDataBinder camelBinder = new FiledKeyRequestDataBinder(binder.getTarget(),
        binder.getObjectName());
    RequestMappingHandlerAdapter requestMappingHandlerAdapter = applicationContext
        .getBean(RequestMappingHandlerAdapter.class);
    requestMappingHandlerAdapter.getWebBindingInitializer().initBinder(camelBinder, request);
    camelBinder.bind(request.getNativeRequest(ServletRequest.class));
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}