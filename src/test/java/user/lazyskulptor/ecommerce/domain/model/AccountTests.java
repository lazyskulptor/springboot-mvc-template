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
		Account u1 = Account.builder().id(1l).build();
		Account u2 = Account.builder().id(1l).build();
		Account u3 = u1.toBuilder().id(2l).build();

		assertThat(u1.equals(u2)).isTrue();
		assertThat(u1.equals(u3)).isFalse();
	}
}
