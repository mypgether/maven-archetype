package ${package}.facade.common;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 提供版本号接口
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
@RequestMapping("/ms/v1")
public interface VersionFacade {

  /**
   * 检测version接口
   */
  @RequestMapping("/version")
  public String getVersion();
}