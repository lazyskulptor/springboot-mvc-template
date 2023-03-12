package user.lazyskulptor.ecommerce.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * LiquibaseConfig
 */
@Configuration
@EnableJpaRepositories({ "user.lazyskulptor.ecommerce.repo" })
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableTransactionManagement
public class LiquibaseConfig {

	@Bean
	public SpringLiquibase liquibase(DataSource source, LiquibaseProperties props) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setChangeLog(props.getChangeLog());
		liquibase.setDataSource(source);

		return liquibase;
	}
}
