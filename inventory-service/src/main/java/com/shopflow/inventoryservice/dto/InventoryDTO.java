package com.shopflow.inventoryservice.dto;
import jakarta.validation.constraints.*;

public class InventoryDTO {
    @NotNull(message = "El ID de producto es obligatorio")
    private Long productId;
    @NotNull @Min(value = 0, message = "La cantidad no puede ser negativa")
    private Integer quantity;
    @Min(value = 0) private Integer minimumStock = 5;

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Integer getMinimumStock() { return minimumStock; }
    public void setMinimumStock(Integer minimumStock) { this.minimumStock = minimumStock; }
}
