package user.lazyskulptor.ecommerce.domain.model;

import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Authority
 */
@Entity
@Getter
@ToString
@Embeddable
@NoArgsConstructor
public class Authority {

    @Id
	private String name;

    public Authority(String name) {
		this.name = name;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authority)) return false;
        if (name == null) return false;
		return Objects.equals(name, ((Authority) o).getName());
    }

    @Override
    public int hashCode() {
		return Objects.hash(name);
    }
}
