package com.shopflow.paymentservice.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class CreatePaymentDTO {
    @NotNull private Long orderId;
    @NotNull private Long userId;
    @NotNull @DecimalMin("0.01") private BigDecimal amount;
    @NotBlank private String method;

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
}
