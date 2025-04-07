package com.api.nursery_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.nursery_system.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByRoleName(String role);

}
