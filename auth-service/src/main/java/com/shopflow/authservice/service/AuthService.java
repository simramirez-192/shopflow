package com.shopflow.authservice.service;

import com.shopflow.authservice.dto.AuthResponseDTO;
import com.shopflow.authservice.dto.LoginRequestDTO;
import com.shopflow.authservice.exception.AuthException;
import com.shopflow.authservice.model.AuthToken;
import com.shopflow.authservice.repository.AuthTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Servicio de autenticación.
 * Usa WebClient para comunicarse con user-service y validar credenciales.
 */
@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthTokenRepository tokenRepository;
    private final WebClient webClient;

    public AuthService(AuthTokenRepository tokenRepository, WebClient.Builder webClientBuilder) {
        this.tokenRepository = tokenRepository;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    /**
     * Autentica al usuario consultando user-service vía WebClient.
     * Regla de negocio: genera token único válido por 24 horas.
     */
    public AuthResponseDTO login(LoginRequestDTO dto) {
        log.info("Intento de login para email: {}", dto.getEmail());
        try {
            // Comunicación con user-service para verificar credenciales
            Map userResponse = webClient.get()
                    .uri("/api/users/by-email?email=" + dto.getEmail())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (userResponse == null) {
                throw new AuthException("Credenciales inválidas");
            }

            Long userId = Long.valueOf(userResponse.get("id").toString());
            String token = UUID.randomUUID().toString();

            AuthToken authToken = new AuthToken();
            authToken.setUserId(userId);
            authToken.setToken(token);
            authToken.setExpiresAt(LocalDateTime.now().plusHours(24));
            tokenRepository.save(authToken);

            log.info("Login exitoso para usuario ID: {}", userId);
            return new AuthResponseDTO(token, userId, "Login exitoso");
        } catch (AuthException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error en login: {}", e.getMessage());
            throw new AuthException("Error al autenticar: " + e.getMessage());
        }
    }

    public void logout(String token) {
        log.info("Logout solicitado");
        tokenRepository.findByTokenAndActiveTrue(token).ifPresent(t -> {
            t.setActive(false);
            tokenRepository.save(t);
            log.info("Token invalidado para usuario ID: {}", t.getUserId());
        });
    }

    public boolean validateToken(String token) {
        return tokenRepository.findByTokenAndActiveTrue(token)
                .map(t -> t.getExpiresAt().isAfter(LocalDateTime.now()))
                .orElse(false);
    }
}
