package ru.sbt.userservice.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:connection.properties")
@ComponentScan
@EnableTransactionManagement
public class ServiceConfig {
    @Autowired
    Environment environment;

    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        return new DriverManagerDataSource(environment.getProperty("db.url"),
                environment.getProperty("db.username"),
                environment.getProperty("db.password"));
    }

    /**
     * Create JdbcTemplate
     * @return JdbcTemplate
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(driverManagerDataSource());
        //jdbcTemplate.setExceptionTranslator(new SqlErrorCodeTraslator());
        return jdbcTemplate;
    }

    /**
     * Create NamedParameterJdbcTemplate
     * @param jdbcTemplate JdbcTemplate
     * @return NamedParameterJdbcTemplate
     */
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    /**
     * Create PlatformTransactionManager
     * @param dataSource DataSource
     * @return PlatformTransactionManager
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
