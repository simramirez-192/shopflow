package com.shopflow.notificationservice.dto;
import com.shopflow.notificationservice.model.Notification;
import java.time.LocalDateTime;

public class NotificationResponseDTO {
    private Long id;
    private Long userId;
    private String title;
    private String message;
    private String type;
    private Boolean read;
    private LocalDateTime createdAt;

    public static NotificationResponseDTO fromEntity(Notification n) {
        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.id = n.getId(); dto.userId = n.getUserId(); dto.title = n.getTitle();
        dto.message = n.getMessage(); dto.type = n.getType().name();
        dto.read = n.getRead(); dto.createdAt = n.getCreatedAt();
        return dto;
    }
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getType() { return type; }
    public Boolean getRead() { return read; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
