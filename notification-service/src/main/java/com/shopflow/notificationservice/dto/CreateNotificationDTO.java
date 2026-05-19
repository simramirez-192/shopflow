package com.shopflow.notificationservice.dto;
import jakarta.validation.constraints.*;

public class CreateNotificationDTO {
    @NotNull private Long userId;
    @NotBlank @Size(max = 200) private String title;
    @NotBlank private String message;
    @NotBlank private String type;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
