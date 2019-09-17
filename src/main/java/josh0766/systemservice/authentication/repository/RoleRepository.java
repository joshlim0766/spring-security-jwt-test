package josh0766.systemservice.authentication.repository;

import josh0766.systemservice.authentication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findOneByName (String name);
}
