package com.shopflow.inventoryservice.dto;
import com.shopflow.inventoryservice.model.Inventory;
import java.time.LocalDateTime;

public class InventoryResponseDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Integer minimumStock;
    private Boolean lowStock;
    private LocalDateTime updatedAt;

    public static InventoryResponseDTO fromEntity(Inventory inv) {
        InventoryResponseDTO dto = new InventoryResponseDTO();
        dto.id = inv.getId(); dto.productId = inv.getProductId();
        dto.quantity = inv.getQuantity(); dto.minimumStock = inv.getMinimumStock();
        dto.lowStock = inv.getQuantity() <= inv.getMinimumStock();
        dto.updatedAt = inv.getUpdatedAt();
        return dto;
    }
    public Long getId() { return id; }
    public Long getProductId() { return productId; }
    public Integer getQuantity() { return quantity; }
    public Integer getMinimumStock() { return minimumStock; }
    public Boolean getLowStock() { return lowStock; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
