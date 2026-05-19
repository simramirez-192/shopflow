package com.shopflow.productservice.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class CreateProductDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 200)
    private String name;
    private String description;
    @NotNull @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal price;
    @NotBlank(message = "La categoría es obligatoria")
    private String category;
    private String brand;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
}
