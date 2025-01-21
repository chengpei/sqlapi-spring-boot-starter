package top.chengpei.sqlapi.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnClass({JdbcTemplate.class})
@EnableConfigurationProperties(SqlapiProperties.class)
@ComponentScans(@ComponentScan("top.chengpei.sqlapi.modules"))
@AutoConfigureAfter({JdbcTemplateAutoConfiguration.class})
public class SqlapiAutoConfiguration implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(SqlapiAutoConfiguration.class);

    private JdbcTemplate jdbcTemplate;

    public SqlapiAutoConfiguration(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void afterPropertiesSet() {
        Long count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM information_schema.TABLES WHERE table_name='t_api_generate_config'", Long.class);
        if (count > 0) {
            return;
        }
        logger.info("接口生成表初始化。。。");
        String createTableSql = "create table t_api_generate_config\n" +
                "(\n" +
                "    api_postfix       varchar(200) not null comment '接口后缀'\n" +
                "        primary key,\n" +
                "    api_name          varchar(100) not null comment '接口名称',\n" +
                "    statement_content text         not null comment 'sql语句'\n" +
                ")";
        String initDataSql = "INSERT INTO t_api_generate_config (api_postfix, api_name, statement_content) VALUES ('test', '测试接口', 'select * from t_api_generate_config')";
        logger.info("创建表：");
        logger.info(createTableSql);
        jdbcTemplate.execute(createTableSql);
        logger.info("初始化数据：");
        logger.info(initDataSql);
        jdbcTemplate.execute(initDataSql);
        logger.info("接口生成表初始化完成。。。");
    }
}
