package josh0766.systemservice.common.repository;

import josh0766.systemservice.common.model.SystemServiceConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemServiceConfigRepository extends JpaRepository<SystemServiceConfig, String> {
    SystemServiceConfig findByKey (String key);
}
