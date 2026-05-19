package com.shopflow.userservice.service;

import com.shopflow.userservice.dto.CreateUserDTO;
import com.shopflow.userservice.dto.UserResponseDTO;
import com.shopflow.userservice.exception.UserAlreadyExistsException;
import com.shopflow.userservice.exception.UserNotFoundException;
import com.shopflow.userservice.model.User;
import com.shopflow.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Capa de servicio para la lógica de negocio de usuarios.
 * Contiene validaciones de negocio y coordinación con el repositorio.
 */
@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Crea un nuevo usuario verificando que el email no esté duplicado.
     */
    public UserResponseDTO createUser(CreateUserDTO dto) {
        log.info("Intentando crear usuario con email: {}", dto.getEmail());

        if (userRepository.existsByEmail(dto.getEmail())) {
            log.warn("Email ya registrado: {}", dto.getEmail());
            throw new UserAlreadyExistsException("El email ya está registrado: " + dto.getEmail());
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        // En producción: usar BCrypt para encriptar la contraseña
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());

        User savedUser = userRepository.save(user);
        log.info("Usuario creado exitosamente con ID: {}", savedUser.getId());

        return UserResponseDTO.fromEntity(savedUser);
    }

    public UserResponseDTO getUserById(Long id) {
        log.info("Buscando usuario con ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con ID: {}", id);
                    return new UserNotFoundException("Usuario no encontrado con ID: " + id);
                });
        return UserResponseDTO.fromEntity(user);
    }

    public List<UserResponseDTO> getAllUsers() {
        log.info("Obteniendo lista de todos los usuarios");
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public UserResponseDTO updateUser(Long id, CreateUserDTO dto) {
        log.info("Actualizando usuario ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + id));

        user.setFullName(dto.getFullName());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setUpdatedAt(LocalDateTime.now());

        User updated = userRepository.save(user);
        log.info("Usuario ID {} actualizado correctamente", id);
        return UserResponseDTO.fromEntity(updated);
    }

    public void deleteUser(Long id) {
        log.info("Eliminando usuario ID: {}", id);
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Usuario no encontrado con ID: " + id);
        }
        userRepository.deleteById(id);
        log.info("Usuario ID {} eliminado", id);
    }
}
