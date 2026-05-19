package com.shopflow.cartservice.service;
import com.shopflow.cartservice.dto.AddToCartDTO;
import com.shopflow.cartservice.dto.CartResponseDTO;
import com.shopflow.cartservice.exception.CartException;
import com.shopflow.cartservice.model.CartItem;
import com.shopflow.cartservice.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio de carrito de compras.
 * Regla de negocio: si el producto ya está en el carrito, incrementa cantidad.
 */
@Service
public class CartService {
    private static final Logger log = LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) { this.cartRepository = cartRepository; }

    public CartResponseDTO addToCart(AddToCartDTO dto) {
        log.info("Agregando producto {} al carrito del usuario {}", dto.getProductId(), dto.getUserId());
        CartItem item = cartRepository.findByUserIdAndProductId(dto.getUserId(), dto.getProductId())
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setUserId(dto.getUserId());
                    newItem.setProductId(dto.getProductId());
                    newItem.setProductName(dto.getProductName());
                    newItem.setUnitPrice(dto.getUnitPrice());
                    newItem.setQuantity(0);
                    return newItem;
                });
        item.setQuantity(item.getQuantity() + dto.getQuantity());
        CartItem saved = cartRepository.save(item);
        log.info("Carrito actualizado. Ítem ID: {}", saved.getId());
        return CartResponseDTO.fromEntity(saved);
    }

    public List<CartResponseDTO> getCartByUser(Long userId) {
        return cartRepository.findByUserId(userId).stream()
                .map(CartResponseDTO::fromEntity).collect(Collectors.toList());
    }

    public Map<String, Object> getCartTotal(Long userId) {
        List<CartItem> items = cartRepository.findByUserId(userId);
        BigDecimal total = items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return Map.of("userId", userId, "itemCount", items.size(), "total", total);
    }

    public void removeItem(Long itemId) {
        if (!cartRepository.existsById(itemId)) throw new CartException("Ítem no encontrado: " + itemId);
        cartRepository.deleteById(itemId);
        log.info("Ítem {} eliminado del carrito", itemId);
    }

    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
        log.info("Carrito vaciado para usuario {}", userId);
    }
}
