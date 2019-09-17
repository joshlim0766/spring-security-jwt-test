package com.pentasecurity.isignplus.systemservice.authentication.repository;

import com.pentasecurity.isignplus.systemservice.authentication.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findOneByName (String name);
}
