package com.shopflow.reviewservice.dto;
import com.shopflow.reviewservice.model.Review;
import java.time.LocalDateTime;

public class ReviewResponseDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer rating;
    private String comment;
    private Boolean verified;
    private LocalDateTime createdAt;

    public static ReviewResponseDTO fromEntity(Review r) {
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.id = r.getId(); dto.userId = r.getUserId(); dto.productId = r.getProductId();
        dto.rating = r.getRating(); dto.comment = r.getComment();
        dto.verified = r.getVerified(); dto.createdAt = r.getCreatedAt();
        return dto;
    }
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getProductId() { return productId; }
    public Integer getRating() { return rating; }
    public String getComment() { return comment; }
    public Boolean getVerified() { return verified; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
