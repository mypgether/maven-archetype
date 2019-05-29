package ${package}.model.req;

import java.io.Serializable;

/**
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
public class VersionReq extends BaseReq {

  private String version;

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }
}