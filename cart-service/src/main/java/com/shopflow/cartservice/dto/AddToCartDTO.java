package com.shopflow.cartservice.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class AddToCartDTO {
    @NotNull private Long userId;
    @NotNull private Long productId;
    @NotBlank private String productName;
    @NotNull @Min(1) private Integer quantity;
    @NotNull @DecimalMin("0.01") private BigDecimal unitPrice;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}
