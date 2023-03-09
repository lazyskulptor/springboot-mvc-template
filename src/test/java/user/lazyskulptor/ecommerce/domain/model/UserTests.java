package user.lazyskulptor.ecommerce.domain.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
/**
 * UserTests
 */
public class UserTests {

	@Test
	void testEqualityWhenNull() {
		User u1 = User.builder().build();
		User u2 = User.builder().build();

		assertThat(u1.equals(u2)).isFalse();
	}

	@Test
	void testEqualityPrototype() {
		User u1 = User.builder().id(1l).build();
		User u2 = User.builder().id(1l).build();
		User u3 = u1.toBuilder().id(2l).build();

		assertThat(u1.equals(u2)).isTrue();
		assertThat(u1.equals(u3)).isFalse();
	}
}
