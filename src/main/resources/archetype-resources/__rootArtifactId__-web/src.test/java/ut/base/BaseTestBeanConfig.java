package ${package}.ut.base;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.h2.tools.Server;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * 单元测试的sqlsession和datasource配置
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
@Configuration
public class BaseTestBeanConfig {

  @Bean(name = "dataSource")
  public DataSource dataSource() {
    EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();

    EmbeddedDatabase db = databaseBuilder
        .setType(EmbeddedDatabaseType.H2)
        //启动时初始化建表语句
        .addScript("classpath:schema/data.sql")
        .build();
    return db;
  }

  @Bean(name = "h2WebServer", initMethod = "start", destroyMethod = "stop")
  //启动一个H2的web server， 调试时可以通过localhost:8082访问到H2的内容
  //JDBC URL: jdbc:h2:mem:testdb
  //User Name: sa
  //Password: 无
  //注意如果使用断点，断点类型(Suspend Type)一定要设置成Thread而不能是All,否则web server无法正常访问!
  public Server server() throws Exception {
    Server server = Server
        .createWebServer("-web", "-webAllowOthers", "-webDaemon", "-webPort", "8082");
    return server;
  }

  @Bean(name = "sqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    Resource[] mapperLocations = resolver.getResources("classpath:META-INF/mapper/*Mapper.xml");
    sessionFactory.setMapperLocations(mapperLocations);
    Resource configLocation = resolver.getResource("classpath:META-INF/spring/mybatis-config.xml");
    sessionFactory.setConfigLocation(configLocation);
    return sessionFactory.getObject();
  }

  @Bean(name = "mapperScannerConfigurer")
  public MapperScannerConfigurer mapperScannerConfigurer() {
    MapperScannerConfigurer configurer = new MapperScannerConfigurer();
    configurer.setBasePackage("${package}.dao");
    configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
    return configurer;
  }
}