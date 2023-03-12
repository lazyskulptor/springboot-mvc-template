package user.lazyskulptor.ecommerce;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import user.lazyskulptor.ecommerce.config.LiquibaseConfig;

/**
 * RepoTest
 */

@Import(LiquibaseConfig.class)
@DataJpaTest(properties = "spring.config.location=classpath:/application-test.yml")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepoTest {
}
