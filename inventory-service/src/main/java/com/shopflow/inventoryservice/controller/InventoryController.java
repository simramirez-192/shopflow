package com.shopflow.inventoryservice.controller;
import com.shopflow.inventoryservice.dto.InventoryDTO;
import com.shopflow.inventoryservice.dto.InventoryResponseDTO;
import com.shopflow.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);
    private final InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService) { this.inventoryService = inventoryService; }

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> create(@Valid @RequestBody InventoryDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventory(dto));
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<InventoryResponseDTO> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getByProductId(productId));
    }
    @PatchMapping("/product/{productId}/reduce")
    public ResponseEntity<InventoryResponseDTO> reduce(@PathVariable Long productId, @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.reduceStock(productId, quantity));
    }
    @PatchMapping("/product/{productId}/add")
    public ResponseEntity<InventoryResponseDTO> add(@PathVariable Long productId, @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.addStock(productId, quantity));
    }
}
