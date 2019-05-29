package ${package}.core.base;

/**
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
public enum ErrorCode {

  SUCCESS("200", "success"),
  CODE_20001("20001", "system error"),
  CODE_20002("20002", "request a buzy system"),
  CODE_20021("20021", "missing request parameter"),
  CODE_20022("20022", "parameter invalid"),
  CODE_20023("20023", "client_id invalid"),
  CODE_20024("20024", "sign error"),
  CODE_20029("20029", "token invalid"),

  // bussiness define
  ;

  private String code;
  private String message;

  ErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
