package user.lazyskulptor.ecommerce.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import user.lazyskulptor.ecommerce.RepoTest;

/**
 * FakeDataTest
 */
@TestPropertySource(properties = "spring.liquibase.contexts=faker")
@DisplayName("Selecting Test with Fake data")
@RepoTest
public class FakeDataTest {

	@Autowired
	AccountRepository repo;

	@Test
	void testFinds() throws SQLException {
		var list = repo.findAll();

		assertThat(list.get(0).getAuthorities()).hasSizeGreaterThan(0);
	}
}
