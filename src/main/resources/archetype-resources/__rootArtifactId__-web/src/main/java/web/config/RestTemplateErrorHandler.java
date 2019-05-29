package ${package}.web.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * resttemplate的错误handler
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {

  @Override
  protected boolean hasError(HttpStatus statusCode) {
    return false;
  }
}