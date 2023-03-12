package user.lazyskulptor.ecommerce.domain.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * AccountTests
 */
public class AccountTests {

	@Test
	void testEqualityWhenNull() {
		Account u1 = Account.builder().build();
		Account u2 = Account.builder().build();

		assertThat(u1.equals(u2)).isFalse();
	}

	@Test
	void testEqualityPrototype() {
		Account u1 = Account.builder().id(1L).build();
		Account u2 = Account.builder().id(1L).build();
		Account u3 = u1.toBuilder().id(2L).build();

		assertThat(u1.getId().equals(u2.getId())).isTrue();
		assertThat(u1.equals(u3)).isFalse();
	}
}
