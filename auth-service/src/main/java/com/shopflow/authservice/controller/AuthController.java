package com.shopflow.authservice.controller;
import com.shopflow.authservice.dto.AuthResponseDTO;
import com.shopflow.authservice.dto.LoginRequestDTO;
import com.shopflow.authservice.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        log.info("POST /api/auth/login");
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token.replace("Bearer ", ""));
        return ResponseEntity.ok(Map.of("message", "Logout exitoso"));
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Boolean>> validate(@RequestHeader("Authorization") String token) {
        boolean valid = authService.validateToken(token.replace("Bearer ", ""));
        return ResponseEntity.ok(Map.of("valid", valid));
    }
}
