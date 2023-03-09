package user.lazyskulptor.ecommerce.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * AccountControllerTests
 */
@SpringBootTest(properties = "spring.config.location=classpath:/application.yml")
@AutoConfigureMockMvc
public class AccountControllerTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void testHelloWorld() throws Exception {
		mvc.perform(get("/api"))
			.andExpect(status().isOk())
			.andExpect(content().string("hello World!"));
	}
}
