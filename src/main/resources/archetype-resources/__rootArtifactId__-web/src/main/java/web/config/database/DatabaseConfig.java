package ${package}.web.config.database;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis的包扫描
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
@Configuration
@MapperScan({"${package}.dao.mapper"})
public class DatabaseConfig {

}