package com.shopflow.reviewservice.service;
import com.shopflow.reviewservice.dto.CreateReviewDTO;
import com.shopflow.reviewservice.dto.ReviewResponseDTO;
import com.shopflow.reviewservice.exception.ReviewException;
import com.shopflow.reviewservice.model.Review;
import com.shopflow.reviewservice.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio de reseñas.
 * Regla de negocio: un usuario solo puede reseñar un producto una vez.
 */
@Service
public class ReviewService {
    private static final Logger log = LoggerFactory.getLogger(ReviewService.class);
    private final ReviewRepository reviewRepository;
    public ReviewService(ReviewRepository reviewRepository) { this.reviewRepository = reviewRepository; }

    public ReviewResponseDTO createReview(CreateReviewDTO dto) {
        log.info("Creando reseña: usuario {} - producto {}", dto.getUserId(), dto.getProductId());
        if (reviewRepository.existsByUserIdAndProductId(dto.getUserId(), dto.getProductId())) {
            throw new ReviewException("El usuario ya reseñó este producto");
        }
        Review r = new Review();
        r.setUserId(dto.getUserId()); r.setProductId(dto.getProductId());
        r.setRating(dto.getRating()); r.setComment(dto.getComment());
        Review saved = reviewRepository.save(r);
        log.info("Reseña creada ID: {}", saved.getId());
        return ReviewResponseDTO.fromEntity(saved);
    }

    public List<ReviewResponseDTO> getByProduct(Long productId) {
        return reviewRepository.findByProductId(productId).stream()
                .map(ReviewResponseDTO::fromEntity).collect(Collectors.toList());
    }

    public Map<String, Object> getProductStats(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        Double avg = reviewRepository.getAverageRatingByProductId(productId);
        return Map.of("productId", productId, "totalReviews", reviews.size(), "averageRating", avg != null ? avg : 0.0);
    }

    public List<ReviewResponseDTO> getByUser(Long userId) {
        return reviewRepository.findByUserId(userId).stream()
                .map(ReviewResponseDTO::fromEntity).collect(Collectors.toList());
    }
}
