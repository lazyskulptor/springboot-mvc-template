package user.lazyskulptor.ecommerce.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.ServletOutputStream;
import java.util.Map;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain h2FilterChain(HttpSecurity http) throws Exception {
        return http.antMatcher("/h2-console/**").build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
            .authorizeRequests()
                .anyRequest().authenticated()
            .and()
                .oauth2Login()
                .successHandler((request, response, authentication) -> {
                    ServletOutputStream writer = response.getOutputStream();
                    response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                    OidcUser user = (OidcUser)authentication.getPrincipal();
                    mapper.writeValue(writer, Map.of("token", user.getIdToken().getTokenValue()));
                    writer.close();
                })
            .and()
                .oauth2ResourceServer()
                .jwt()
                .and()
            .and()
                .oauth2Client();
        return http.build();
    }
}
