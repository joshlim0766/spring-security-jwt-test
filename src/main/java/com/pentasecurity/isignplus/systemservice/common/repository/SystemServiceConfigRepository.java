package com.pentasecurity.isignplus.systemservice.common.repository;

import com.pentasecurity.isignplus.systemservice.common.model.SystemServiceConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemServiceConfigRepository extends JpaRepository<SystemServiceConfig, String> {
    SystemServiceConfig findByKey (String key);
}
