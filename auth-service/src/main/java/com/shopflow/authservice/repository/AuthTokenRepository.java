package com.shopflow.authservice.repository;
import com.shopflow.authservice.model.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    Optional<AuthToken> findByTokenAndActiveTrue(String token);
    void deleteByUserId(Long userId);
}
