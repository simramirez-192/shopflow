package com.shopflow.orderservice.dto;

import jakarta.validation.constraints.*;
import java.util.List;

/**
 * DTO para creación de un pedido con sus ítems.
 */
public class CreateOrderDTO {

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long userId;

    @NotBlank(message = "La dirección de envío es obligatoria")
    private String shippingAddress;

    @NotEmpty(message = "El pedido debe tener al menos un ítem")
    private List<OrderItemDTO> items;

    public static class OrderItemDTO {
        @NotNull(message = "El ID de producto es obligatorio")
        private Long productId;

        @NotBlank(message = "El nombre del producto es obligatorio")
        private String productName;

        @NotNull @Min(value = 1, message = "La cantidad debe ser al menos 1")
        private Integer quantity;

        @NotNull @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        private java.math.BigDecimal unitPrice;

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public java.math.BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(java.math.BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public List<OrderItemDTO> getItems() { return items; }
    public void setItems(List<OrderItemDTO> items) { this.items = items; }
}
