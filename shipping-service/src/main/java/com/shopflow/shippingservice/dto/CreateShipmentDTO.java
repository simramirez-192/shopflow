package com.shopflow.shippingservice.dto;
import jakarta.validation.constraints.*;

public class CreateShipmentDTO {
    @NotNull private Long orderId;
    @NotBlank private String destination;

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
}
