package user.lazyskulptor.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import user.lazyskulptor.ecommerce.domain.model.User;

/**
 * UserRepository
 */
public interface UserRepository extends JpaRepository<User, Long> {}
