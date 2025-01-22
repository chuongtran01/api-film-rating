package com.personal.api_auth_base.repository;

import com.personal.api_auth_base.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
