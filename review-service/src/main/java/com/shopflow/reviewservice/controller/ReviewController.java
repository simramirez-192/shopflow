package com.shopflow.reviewservice.controller;
import com.shopflow.reviewservice.dto.CreateReviewDTO;
import com.shopflow.reviewservice.dto.ReviewResponseDTO;
import com.shopflow.reviewservice.service.ReviewService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService) { this.reviewService = reviewService; }

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> create(@Valid @RequestBody CreateReviewDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createReview(dto));
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponseDTO>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getByProduct(productId));
    }
    @GetMapping("/product/{productId}/stats")
    public ResponseEntity<Map<String, Object>> getStats(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getProductStats(productId));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponseDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getByUser(userId));
    }
}
