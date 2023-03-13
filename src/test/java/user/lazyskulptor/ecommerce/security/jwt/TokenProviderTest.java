package user.lazyskulptor.ecommerce.security.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;
import user.lazyskulptor.ecommerce.domain.model.Authority;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class TokenProviderTest {

    private static final long ONE_MINUTE = 60000;

    private TokenProvider tokenProvider;
    private final String secret = "this-is-basic=jwt-secret-key-it-has-to-be-overwritten-you-can-use= command-openssl rand -hex 64";

    @BeforeEach
    void setUp() {
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
    void testWhenJWTHasInvalidSignature() {
        TokenProvider invalidTokenProvider = new TokenProvider();
        String invalidSecret = secret + RandomStringUtils.randomAlphabetic(5);
        ReflectionTestUtils.setField(invalidTokenProvider, "secret", invalidSecret);
        ReflectionTestUtils.invokeMethod(invalidTokenProvider, "init");

        Authentication authentication = createAuthentication();
        String invalidToken = invalidTokenProvider.createToken(authentication, false);
        boolean isTokenValid = tokenProvider.validateToken(invalidToken);

        assertThat(isTokenValid).isFalse();
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

    @Test
    void testKeyIsSetFromSecretWhenSecretIsNotEmpty() {
        final String secret = "NwskoUmKHZtzGRKJKVjsJF7BtQMMxNWi";

        TokenProvider tokenProvider = new TokenProvider();
        ReflectionTestUtils.setField(tokenProvider, "secret", secret);
        ReflectionTestUtils.invokeMethod(tokenProvider, "init");

        Key key = (Key) ReflectionTestUtils.getField(tokenProvider, "key");
        assertThat(key).isNotNull().isEqualTo(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void testKeyIsSetFromBase64SecretWhenSecretIsEmpty() {
        final String base64Secret = "fd54a45s65fds737b9aafcb3412e07ed99b267f33413274720ddbb7f6c5e64e9f14075f2d7ed041592f0b7657baf8";

        TokenProvider tokenProvider = new TokenProvider();
        ReflectionTestUtils.setField(tokenProvider, "base64Secret", base64Secret);
        ReflectionTestUtils.invokeMethod(tokenProvider, "init");

        Key key = (Key) ReflectionTestUtils.getField(tokenProvider, "key");
        assertThat(key).isNotNull().isEqualTo(Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret)));
    }

    public static Authentication createAuthentication() {
        String role = Authority.MEMBER.toString();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return new UsernamePasswordAuthenticationToken(role, role, authorities);
    }
}