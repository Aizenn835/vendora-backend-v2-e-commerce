package com.codewithlei.vendora.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UsersEntity , Long> {
    Optional<UsersEntity> findByEmail(String email);
    Boolean existsByEmail(String email);
}
