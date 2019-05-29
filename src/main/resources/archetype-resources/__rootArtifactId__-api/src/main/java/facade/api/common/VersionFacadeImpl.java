package ${package}.facade.api.common;

import ${package}.facade.api.BaseFacade;
import ${package}.facade.common.VersionFacade;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取版本号
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
@RestController
@Slf4j
public class VersionFacadeImpl extends BaseFacade implements VersionFacade {

  private static volatile String version;

  @Override
  public String getVersion() {
    if (StringUtils.isBlank(version)) {
      ClassPathResource res = new ClassPathResource("version.txt");
      try {
        version = new BufferedReader(new InputStreamReader(res.getInputStream())).lines()
            .collect(Collectors.joining(System.lineSeparator()));
      } catch (IOException e) {
        logger.error("getVersion error, ", e);
      }
    }
    return version;
  }
}