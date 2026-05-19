package com.shopflow.notificationservice.service;
import com.shopflow.notificationservice.dto.CreateNotificationDTO;
import com.shopflow.notificationservice.dto.NotificationResponseDTO;
import com.shopflow.notificationservice.exception.NotificationException;
import com.shopflow.notificationservice.model.Notification;
import com.shopflow.notificationservice.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;
    public NotificationService(NotificationRepository notificationRepository) { this.notificationRepository = notificationRepository; }

    public NotificationResponseDTO send(CreateNotificationDTO dto) {
        log.info("Enviando notificación tipo {} al usuario {}", dto.getType(), dto.getUserId());
        Notification n = new Notification();
        n.setUserId(dto.getUserId()); n.setTitle(dto.getTitle()); n.setMessage(dto.getMessage());
        try { n.setType(Notification.NotificationType.valueOf(dto.getType().toUpperCase())); }
        catch (IllegalArgumentException e) { throw new NotificationException("Tipo inválido: " + dto.getType()); }
        Notification saved = notificationRepository.save(n);
        log.info("Notificación enviada ID: {}", saved.getId());
        return NotificationResponseDTO.fromEntity(saved);
    }

    public List<NotificationResponseDTO> getByUser(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(NotificationResponseDTO::fromEntity).collect(Collectors.toList());
    }

    public List<NotificationResponseDTO> getUnreadByUser(Long userId) {
        return notificationRepository.findByUserIdAndReadFalse(userId).stream()
                .map(NotificationResponseDTO::fromEntity).collect(Collectors.toList());
    }

    public NotificationResponseDTO markAsRead(Long id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationException("Notificación no encontrada: " + id));
        n.setRead(true);
        return NotificationResponseDTO.fromEntity(notificationRepository.save(n));
    }
}
