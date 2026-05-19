package com.shopflow.paymentservice.dto;
import com.shopflow.paymentservice.model.Payment;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponseDTO {
    private Long id;
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
    private String status;
    private String method;
    private String transactionId;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;

    public static PaymentResponseDTO fromEntity(Payment p) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.id = p.getId(); dto.orderId = p.getOrderId(); dto.userId = p.getUserId();
        dto.amount = p.getAmount(); dto.status = p.getStatus().name();
        dto.method = p.getMethod().name(); dto.transactionId = p.getTransactionId();
        dto.createdAt = p.getCreatedAt(); dto.processedAt = p.getProcessedAt();
        return dto;
    }
    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public Long getUserId() { return userId; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus() { return status; }
    public String getMethod() { return method; }
    public String getTransactionId() { return transactionId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getProcessedAt() { return processedAt; }
}
