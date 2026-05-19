package com.shopflow.userservice.repository;

import com.shopflow.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio JPA para acceso a datos de usuarios.
 * Extiende JpaRepository para operaciones CRUD automáticas.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
