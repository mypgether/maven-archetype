package ${package}.web.config;

import ${package}.core.base.ErrorCode;
import ${package}.core.base.ResponseModel;
import ${package}.core.base.ServiceException;
import java.util.List;
import javax.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获处理
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BindException.class)
  public ResponseModel processValidationError(BindException ex) {
    BindingResult result = ex.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    StringBuilder stringBuilder = new StringBuilder();
    for (FieldError fieldError : fieldErrors) {
      stringBuilder.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage())
          .append("|");
    }
    ResponseModel<String> em = new ResponseModel<>();
    em.setCode(ErrorCode.CODE_20022.getCode());
    em.setMsg(ErrorCode.CODE_20022.getMessage());
    if (stringBuilder.toString().length() > 0) {
      em.setDesc(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1));
    }
    return em;
  }

  @ExceptionHandler(ServletException.class)
  public ResponseModel processSpringException(ServletException ex) {
    ResponseModel<String> em = new ResponseModel<>();
    if (ex instanceof MissingServletRequestParameterException) {
      em.setCode(ErrorCode.CODE_20021.getCode());
      em.setMsg(ErrorCode.CODE_20021.getMessage());
      em.setDesc(ex.getMessage());
      log.error("here comes error, handleException, error:{}", ex.getMessage());
    } else {
      em.setCode(ErrorCode.CODE_20001.getCode());
      em.setMsg(ErrorCode.CODE_20001.getMessage());
      log.error("here comes error, handleException, ", ex);
    }
    return em;
  }

  @ExceptionHandler(Exception.class)
  public ResponseModel processSpringException(Exception ex) {
    return exceptionToModel(log, ex);
  }

  public static ResponseModel exceptionToModel(Logger log, Throwable ex) {
    boolean printDetail = false;
    ResponseModel<String> em = new ResponseModel<>();
    if (ex instanceof IllegalArgumentException) {
      em.setCode(ErrorCode.CODE_20022.getCode());
      em.setMsg(ErrorCode.CODE_20022.getMessage());
      em.setDesc(ex.getMessage());
    } else if (ex instanceof ServiceException) {
      em.setCode(((ServiceException) ex).getCode());
      em.setMsg(((ServiceException) ex).getMsg());
    } else {
      printDetail = true;
      em.setCode(ErrorCode.CODE_20001.getCode());
      em.setMsg(ErrorCode.CODE_20001.getMessage());
    }
    if (printDetail) {
      log.error("here comes error, handleException,", ex);
    } else {
      log.error("here comes error, handleException {}", ex.getMessage());
    }
    return em;
  }
}