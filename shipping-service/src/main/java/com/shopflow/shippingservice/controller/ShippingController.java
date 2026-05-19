package com.shopflow.shippingservice.controller;
import com.shopflow.shippingservice.dto.CreateShipmentDTO;
import com.shopflow.shippingservice.dto.ShipmentResponseDTO;
import com.shopflow.shippingservice.service.ShippingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipping")
public class ShippingController {
    private static final Logger log = LoggerFactory.getLogger(ShippingController.class);
    private final ShippingService shippingService;
    public ShippingController(ShippingService shippingService) { this.shippingService = shippingService; }

    @PostMapping
    public ResponseEntity<ShipmentResponseDTO> create(@Valid @RequestBody CreateShipmentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shippingService.createShipment(dto));
    }
    @GetMapping("/track/{trackingCode}")
    public ResponseEntity<ShipmentResponseDTO> track(@PathVariable String trackingCode) {
        return ResponseEntity.ok(shippingService.trackByCode(trackingCode));
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<ShipmentResponseDTO> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(shippingService.updateStatus(id, status));
    }
}
