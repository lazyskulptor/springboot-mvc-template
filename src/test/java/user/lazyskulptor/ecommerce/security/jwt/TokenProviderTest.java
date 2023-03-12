package user.lazyskulptor.ecommerce.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;
import user.lazyskulptor.ecommerce.domain.model.Authority;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class TokenProviderTest {

    private static final long ONE_MINUTE = 60000;

    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() {
        String secret = "this-is-basic=jwt-secret-key-it-has-to-be-overwritten-you-can-use= command-openssl rand -hex 64";
        System.out.println(secret);
        tokenProvider = new TokenProvider();
        ReflectionTestUtils.setField(tokenProvider, "secret", secret);
        ReflectionTestUtils.invokeMethod(tokenProvider, "init");
    }

    @Test
    void testCreateToken() {
        Authentication authentication = createAuthentication();
        String token = tokenProvider.createToken(authentication, false);
        boolean isTokenValid = tokenProvider.validateToken(token);

        assertThat(isTokenValid).isTrue();
    }
    @Test
    void testWhenJWTisMalformed() {
        Authentication authentication = createAuthentication();
        String token = tokenProvider.createToken(authentication, false);
        String invalidToken = token.substring(1);
        boolean isTokenValid = tokenProvider.validateToken(invalidToken);

        assertThat(isTokenValid).isFalse();
    }

    @Test
    void testWhenJWTisExpired() {
        ReflectionTestUtils.setField(tokenProvider, "tokenValidityInMilliseconds", -ONE_MINUTE);

        Authentication authentication = createAuthentication();
        String token = tokenProvider.createToken(authentication, false);

        boolean isTokenValid = tokenProvider.validateToken(token);

        assertThat(isTokenValid).isFalse();
    }

    private Authentication createAuthentication() {
        String role = Authority.MEMBER.toString();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return new UsernamePasswordAuthenticationToken(role, role, authorities);
    }
}