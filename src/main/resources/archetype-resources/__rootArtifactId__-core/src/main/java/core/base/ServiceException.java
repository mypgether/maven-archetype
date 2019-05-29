package ${package}.core.base;

/**
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
public class ServiceException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String code;
  private String msg;


  public ServiceException(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.msg = errorCode.getMessage();
  }

  public ServiceException(String code, String msg) {
    super(msg);
    this.code = code;
    this.msg = msg;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  @Override
  public synchronized Throwable fillInStackTrace() {
    return this;
  }
}