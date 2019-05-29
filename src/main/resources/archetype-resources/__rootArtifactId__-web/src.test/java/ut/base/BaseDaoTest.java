package ${package}.ut.base;

import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.ServletTestExecutionListener;

/**
 * 单元测试中database测试的基础类
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ${package}.ut.base.BaseTestBeanConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestExecutionListeners(listeners = {SpringBootDependencyInjectionTestExecutionListener.class,
    ServletTestExecutionListener.class, ClearDataBaseListener.class})
public abstract class BaseDaoTest {

  @Autowired
  protected DataSource dataSource;

  /**
   * Execute SQL directly, these SQL may be for test purpose only and should not be included in
   * mybatis sqlMapper configuration files.
   *
   * @param sql SQL statement to execute, variable binding is NOT supported for simplicity.
   */
  protected void execute(String sql) {
    try {
      dataSource.getConnection().createStatement().execute(sql);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}