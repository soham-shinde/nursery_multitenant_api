package com.api.nursery_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.nursery_system.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailId(String userName);

    Optional<User> findByEmailId(String emailId);

    List<User> findByRoleRoleId(long l);

    Optional<User> findByUserNameAndPassword(String userName, String password);

}
