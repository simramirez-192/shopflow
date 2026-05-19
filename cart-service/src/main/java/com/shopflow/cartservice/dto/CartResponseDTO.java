package com.shopflow.cartservice.dto;
import com.shopflow.cartservice.model.CartItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartResponseDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private LocalDateTime addedAt;

    public static CartResponseDTO fromEntity(CartItem item) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.id = item.getId(); dto.userId = item.getUserId();
        dto.productId = item.getProductId(); dto.productName = item.getProductName();
        dto.quantity = item.getQuantity(); dto.unitPrice = item.getUnitPrice();
        dto.subtotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
        dto.addedAt = item.getAddedAt();
        return dto;
    }
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getSubtotal() { return subtotal; }
    public LocalDateTime getAddedAt() { return addedAt; }
}
