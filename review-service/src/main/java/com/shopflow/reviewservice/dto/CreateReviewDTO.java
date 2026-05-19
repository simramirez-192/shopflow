package com.shopflow.reviewservice.dto;
import jakarta.validation.constraints.*;

public class CreateReviewDTO {
    @NotNull private Long userId;
    @NotNull private Long productId;
    @NotNull @Min(1) @Max(5) private Integer rating;
    @Size(max = 1000) private String comment;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
