package ${package}.ut.base;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * 单元测试后清空db数据
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
public class ClearDataBaseListener extends AbstractTestExecutionListener {

  @Override
  public void afterTestMethod(TestContext testContext) throws Exception {
    DataSource dataSource = testContext.getApplicationContext().getBean(DataSource.class);
    ClassPathResource res = new ClassPathResource("schema/flush.sql");
    String result = new BufferedReader(new InputStreamReader(res.getInputStream())).lines()
        .collect(Collectors.joining(System.lineSeparator()));
    dataSource.getConnection().createStatement().execute(result);
  }
}