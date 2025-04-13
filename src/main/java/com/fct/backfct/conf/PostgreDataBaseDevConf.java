package com.fct.backfct.conf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.fct.backfct.domain.models.dao",
                "com.fct.backfct.security.repository"
        })
public class PostgreDataBaseDevConf {

    @Bean(name = "dataSource", destroyMethod = "")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource postgresqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcTemplatePostgresql")
    public JdbcTemplate jdbcTemplateApp(@Qualifier("dataSource") DataSource dsApp) {
        return new JdbcTemplate(dsApp);
    }

    @Bean(name = "namedTemplatePostgresql")
    public NamedParameterJdbcTemplate namedTemplateApp(@Qualifier("dataSource") DataSource dsApp) {
        return new NamedParameterJdbcTemplate(dsApp);
    }

    @Bean(name= "manualTransactionManagerPostgresql")
    public DataSourceTransactionManager manualTransactionManagerPostgresql(@Qualifier("dataSource") DataSource dsApp) {
        return new DataSourceTransactionManager(dsApp);
    }


}