package com.shopflow.inventoryservice.service;

import com.shopflow.inventoryservice.dto.InventoryDTO;
import com.shopflow.inventoryservice.dto.InventoryResponseDTO;
import com.shopflow.inventoryservice.exception.InventoryException;
import com.shopflow.inventoryservice.model.Inventory;
import com.shopflow.inventoryservice.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/**
 * Lógica de negocio para control de inventario.
 * Regla de negocio: no se puede reducir stock por debajo de 0.
 */
@Service
public class InventoryService {
    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) { this.inventoryRepository = inventoryRepository; }

    public InventoryResponseDTO createInventory(InventoryDTO dto) {
        log.info("Creando inventario para producto ID: {}", dto.getProductId());
        if (inventoryRepository.existsByProductId(dto.getProductId())) {
            throw new InventoryException("Ya existe inventario para el producto: " + dto.getProductId());
        }
        Inventory inv = new Inventory();
        inv.setProductId(dto.getProductId());
        inv.setQuantity(dto.getQuantity());
        inv.setMinimumStock(dto.getMinimumStock());
        return InventoryResponseDTO.fromEntity(inventoryRepository.save(inv));
    }

    public InventoryResponseDTO getByProductId(Long productId) {
        Inventory inv = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryException("Inventario no encontrado para producto: " + productId));
        return InventoryResponseDTO.fromEntity(inv);
    }

    /** Reduce stock validando que no baje de 0 */
    public InventoryResponseDTO reduceStock(Long productId, Integer quantity) {
        log.info("Reduciendo stock del producto {}: -{}", productId, quantity);
        Inventory inv = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryException("Inventario no encontrado para producto: " + productId));
        if (inv.getQuantity() < quantity) {
            throw new InventoryException("Stock insuficiente. Disponible: " + inv.getQuantity() + ", solicitado: " + quantity);
        }
        inv.setQuantity(inv.getQuantity() - quantity);
        inv.setUpdatedAt(LocalDateTime.now());
        log.info("Stock reducido. Nuevo stock del producto {}: {}", productId, inv.getQuantity());
        return InventoryResponseDTO.fromEntity(inventoryRepository.save(inv));
    }

    public InventoryResponseDTO addStock(Long productId, Integer quantity) {
        log.info("Agregando stock al producto {}: +{}", productId, quantity);
        Inventory inv = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryException("Inventario no encontrado: " + productId));
        inv.setQuantity(inv.getQuantity() + quantity);
        inv.setUpdatedAt(LocalDateTime.now());
        return InventoryResponseDTO.fromEntity(inventoryRepository.save(inv));
    }
}
