package user.lazyskulptor.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import user.lazyskulptor.ecommerce.domain.model.Account;

/**
 * AccountRepository
 */
public interface AccountRepository extends JpaRepository<Account, Long> {}
