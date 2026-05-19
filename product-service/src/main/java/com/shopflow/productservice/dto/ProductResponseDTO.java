package com.shopflow.productservice.dto;
import com.shopflow.productservice.model.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String brand;
    private Boolean active;
    private LocalDateTime createdAt;

    public static ProductResponseDTO fromEntity(Product p) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.id = p.getId(); dto.name = p.getName(); dto.description = p.getDescription();
        dto.price = p.getPrice(); dto.category = p.getCategory(); dto.brand = p.getBrand();
        dto.active = p.getActive(); dto.createdAt = p.getCreatedAt();
        return dto;
    }
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getCategory() { return category; }
    public String getBrand() { return brand; }
    public Boolean getActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
