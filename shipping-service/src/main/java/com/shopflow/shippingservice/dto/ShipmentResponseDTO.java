package com.shopflow.shippingservice.dto;
import com.shopflow.shippingservice.model.Shipment;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ShipmentResponseDTO {
    private Long id;
    private Long orderId;
    private String destination;
    private String status;
    private String trackingCode;
    private LocalDate estimatedDelivery;
    private LocalDateTime createdAt;

    public static ShipmentResponseDTO fromEntity(Shipment s) {
        ShipmentResponseDTO dto = new ShipmentResponseDTO();
        dto.id = s.getId(); dto.orderId = s.getOrderId();
        dto.destination = s.getDestination(); dto.status = s.getStatus().name();
        dto.trackingCode = s.getTrackingCode();
        dto.estimatedDelivery = s.getEstimatedDelivery(); dto.createdAt = s.getCreatedAt();
        return dto;
    }
    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public String getDestination() { return destination; }
    public String getStatus() { return status; }
    public String getTrackingCode() { return trackingCode; }
    public LocalDate getEstimatedDelivery() { return estimatedDelivery; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
