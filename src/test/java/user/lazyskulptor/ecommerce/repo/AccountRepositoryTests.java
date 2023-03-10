package user.lazyskulptor.ecommerce.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import user.lazyskulptor.ecommerce.config.LiquibaseConfig;

/**
 * AccountRepositoryTests
 */
@Import(LiquibaseConfig.class)
@DataJpaTest(properties = "spring.config.location=classpath:/application-test.yml")
public class AccountRepositoryTests {

	@Autowired
	AccountRepository repo;

	@Test
	void testFind() throws SQLException {
		var list = repo.findAll();
		assertThat(list.size()).isGreaterThan(0);
	}
}
