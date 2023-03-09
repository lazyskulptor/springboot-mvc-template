package user.lazyskulptor.ecommerce.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import liquibase.integration.spring.SpringLiquibase;

/**
 * LiquibaseConfig
 */
@Configuration
public class LiquibaseConfig {

	@Bean
	public SpringLiquibase liquibase(DataSource source, LiquibaseProperties props) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setChangeLog(props.getChangeLog());
		liquibase.setDataSource(source);

		return liquibase;
	}
}
