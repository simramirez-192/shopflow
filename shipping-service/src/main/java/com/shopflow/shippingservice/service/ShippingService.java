package com.shopflow.shippingservice.service;
import com.shopflow.shippingservice.dto.CreateShipmentDTO;
import com.shopflow.shippingservice.dto.ShipmentResponseDTO;
import com.shopflow.shippingservice.exception.ShipmentException;
import com.shopflow.shippingservice.model.Shipment;
import com.shopflow.shippingservice.repository.ShipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ShippingService {
    private static final Logger log = LoggerFactory.getLogger(ShippingService.class);
    private final ShipmentRepository shipmentRepository;
    public ShippingService(ShipmentRepository shipmentRepository) { this.shipmentRepository = shipmentRepository; }

    public ShipmentResponseDTO createShipment(CreateShipmentDTO dto) {
        log.info("Creando envío para pedido ID: {}", dto.getOrderId());
        if (shipmentRepository.findByOrderId(dto.getOrderId()).isPresent()) {
            throw new ShipmentException("Ya existe un envío para el pedido: " + dto.getOrderId());
        }
        Shipment s = new Shipment();
        s.setOrderId(dto.getOrderId());
        s.setDestination(dto.getDestination());
        s.setTrackingCode("TRK-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase());
        s.setEstimatedDelivery(LocalDate.now().plusDays(5));
        s.setStatus(Shipment.ShipmentStatus.PREPARING);
        Shipment saved = shipmentRepository.save(s);
        log.info("Envío creado. Tracking: {}", saved.getTrackingCode());
        return ShipmentResponseDTO.fromEntity(saved);
    }

    public ShipmentResponseDTO trackByCode(String trackingCode) {
        return ShipmentResponseDTO.fromEntity(shipmentRepository.findByTrackingCode(trackingCode)
                .orElseThrow(() -> new ShipmentException("Código de tracking no encontrado: " + trackingCode)));
    }

    public ShipmentResponseDTO updateStatus(Long id, String status) {
        Shipment s = shipmentRepository.findById(id)
                .orElseThrow(() -> new ShipmentException("Envío no encontrado: " + id));
        try { s.setStatus(Shipment.ShipmentStatus.valueOf(status.toUpperCase())); }
        catch (IllegalArgumentException e) { throw new ShipmentException("Estado inválido: " + status); }
        s.setUpdatedAt(LocalDateTime.now());
        return ShipmentResponseDTO.fromEntity(shipmentRepository.save(s));
    }
}
