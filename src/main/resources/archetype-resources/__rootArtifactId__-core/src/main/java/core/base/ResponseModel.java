package ${package}.core.base;

import java.io.Serializable;

/**
 * http响应
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
public class ResponseModel<T> implements Serializable {

  private static final long serialVersionUID = -5661429081340062999L;
  private String code;
  private String msg;
  private String desc;
  private T data;

  public ResponseModel() {
    this(ErrorCode.SUCCESS);
  }

  public ResponseModel(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.msg = errorCode.getMessage();
  }

  public void setErrorCode(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.msg = errorCode.getMessage();
  }

  public ResponseModel(String code, String msg, String desc, T data) {
    this.code = code;
    this.msg = msg;
    this.desc = desc;
    this.data = data;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return this.msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getDesc() {
    return this.desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public T getData() {
    return this.data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
