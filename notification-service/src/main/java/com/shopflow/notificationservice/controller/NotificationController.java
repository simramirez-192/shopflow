package com.shopflow.notificationservice.controller;
import com.shopflow.notificationservice.dto.CreateNotificationDTO;
import com.shopflow.notificationservice.dto.NotificationResponseDTO;
import com.shopflow.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationService notificationService;
    public NotificationController(NotificationService notificationService) { this.notificationService = notificationService; }

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> send(@Valid @RequestBody CreateNotificationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.send(dto));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationResponseDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getByUser(userId));
    }
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationResponseDTO>> getUnread(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUnreadByUser(userId));
    }
    @PatchMapping("/{id}/read")
    public ResponseEntity<NotificationResponseDTO> markRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }
}
