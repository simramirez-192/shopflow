package com.shopflow.cartservice.controller;
import com.shopflow.cartservice.dto.AddToCartDTO;
import com.shopflow.cartservice.dto.CartResponseDTO;
import com.shopflow.cartservice.service.CartService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private static final Logger log = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;
    public CartController(CartService cartService) { this.cartService = cartService; }

    @PostMapping
    public ResponseEntity<CartResponseDTO> addToCart(@Valid @RequestBody AddToCartDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addToCart(dto));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartResponseDTO>> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUser(userId));
    }
    @GetMapping("/user/{userId}/total")
    public ResponseEntity<Map<String, Object>> getTotal(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartTotal(userId));
    }
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
