package user.lazyskulptor.ecommerce.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import user.lazyskulptor.ecommerce.config.LiquibaseConfig;
import user.lazyskulptor.ecommerce.domain.model.Account;
import user.lazyskulptor.ecommerce.domain.model.Authority;

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
		Authority admin = new Authority("ADMIN");
		Authority member = new Authority("MEMBER");
		Account account = Account.builder()
			.username("park")
			.email("park@test.net")
			.password("1234")
			.enabled(true)
			.authority(Set.of(admin, member))
			.build();

		repo.save(account);

		var persisted = repo.findById(account.getId());

		assertThat(persisted).isNotEmpty();
		assertThat(persisted.get().getAuthority()).hasSizeGreaterThan(0);
	}
}
