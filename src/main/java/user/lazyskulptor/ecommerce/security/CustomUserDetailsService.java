package user.lazyskulptor.ecommerce.security;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import user.lazyskulptor.ecommerce.domain.model.Account;
import user.lazyskulptor.ecommerce.domain.model.Authority;
import user.lazyskulptor.ecommerce.repo.AccountRepository;
import user.lazyskulptor.ecommerce.security.jwt.UserNotActivatedException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public CustomUserDetailsService(AccountRepository accountRepository) { this.accountRepository = accountRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Example<Account>> example;
        Account.AccountBuilder builder = Account.builder();
        String lowerCase = username.toLowerCase();
        StringBuilder errMsg = new StringBuilder("User ");

        if (new EmailValidator().isValid(username, null)) {
            example = Optional.of(Example.of(builder.email(username).build()));
            errMsg.append("with email ").append(username);
        } else  {
            example = Optional.of(Example.of(builder.username(lowerCase).build()));
            errMsg.append(lowerCase);
        }

        return example.map(accountExample -> accountRepository.findOne(accountExample)
                .map(account -> createSpringSecurityUser(username, account))
                .orElseThrow(() -> new UsernameNotFoundException(errMsg.append(" was not found in the database").toString()))).orElse(null);
    }
    private User createSpringSecurityUser(String lowercaseLogin, Account account) {
        if (!account.getEnabled()) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = account
                .getAuthorities()
                .stream()
                .map(Authority::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new User(account.getUsername(), account.getPassword(), grantedAuthorities);
    }
}
