package user.lazyskulptor.ecommerce.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * User
 */
@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String username;

	private String password;

	private String email;

	private String firstName;

	private String lastName;

	private Boolean enabled;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
