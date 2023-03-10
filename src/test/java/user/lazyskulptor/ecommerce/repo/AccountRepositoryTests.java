package user.lazyskulptor.ecommerce.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import user.lazyskulptor.ecommerce.RepoTest;
import user.lazyskulptor.ecommerce.domain.model.Account;
import user.lazyskulptor.ecommerce.domain.model.Authority;

/**
 * AccountRepositoryTests
 */
@RepoTest
public class AccountRepositoryTests {

	@Autowired
	AccountRepository repo;

	@Autowired
	EntityManager entityManager;

	@Test
	void testFind() throws SQLException {
		Set<Authority> auth = new HashSet<>(Authority.values().length); 
		auth.add(Authority.ADMIN);
		auth.add(Authority.MEMBER);
		Account account = Account.builder()
			.username("park")
			.email("park@test.net")
			.password("1234")
			.enabled(true)
			.authorities(auth)
			.build();

		System.out.println(account.getAuthorities());

		repo.saveAndFlush(account);
		entityManager.clear();

		var persisted = repo.findById(account.getId());
		var list = repo.findAll();

		assertThat(persisted).isNotEmpty();
		assertThat(persisted.get().getAuthorities()).hasSizeGreaterThan(0);
		assertThat(list).hasSize(1);
	}
}
