package josh0766.systemservice.authentication.repository;

import josh0766.systemservice.authentication.model.SystemServiceUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SystemServiceUser, Integer> {
    Optional<SystemServiceUser> findOneByUserId (String userId);
}
