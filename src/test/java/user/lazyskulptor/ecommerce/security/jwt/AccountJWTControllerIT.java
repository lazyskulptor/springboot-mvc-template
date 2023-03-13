package user.lazyskulptor.ecommerce.security.jwt;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import user.lazyskulptor.ecommerce.domain.model.Account;
import user.lazyskulptor.ecommerce.domain.model.Authority;
import user.lazyskulptor.ecommerce.repo.AccountRepository;
import user.lazyskulptor.ecommerce.security.dto.LoginVM;

import java.util.Set;

@SpringBootTest(properties = "spring.config.location=classpath:/application-test.yml")
@AutoConfigureMockMvc
public class AccountJWTControllerIT {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    private MockMvc mvc;

    @Test
    void testAuthorize() throws Exception {
        String password = RandomStringUtils.randomAlphanumeric(10);
        Account account = Account.builder()
                .username("username-jwt-ctrl")
                .email("username-jwt-ctrl@test.net")
                .authorities(Set.of(Authority.MEMBER))
                .enabled(true)
                .password(passwordEncoder.encode(password))
                .build();

        accountRepository.saveAndFlush(account);

        LoginVM login = LoginVM.builder()
                .username(account.getUsername())
                .password(password)
                .build();

        byte[] body = new ObjectMapper().writeValueAsBytes(login);
        mvc.perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_token").isNotEmpty())
                .andExpect(header().string("Authorization", not(emptyString())));
    }

    @Test
    void testAuthorizeFails() throws Exception {
        LoginVM login = LoginVM.builder()
                .username("wrong-user")
                .password("wrong password")
                .build();

        byte[] body = new ObjectMapper().writeValueAsBytes(login);
        mvc.perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.id_token").doesNotExist())
                .andExpect(header().doesNotExist("Authorization"));
    }

    @Test
    void testNotAuthorizedAccess() throws Exception {
        mvc.perform(get("/api"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAuthorizedAccess() throws Exception {
        String token = tokenProvider.createToken(TokenProviderTest.createAuthentication(), false);
        mvc.perform(get("/api").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
