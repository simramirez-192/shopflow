package com.shopflow.orderservice.service;

import com.shopflow.orderservice.dto.CreateOrderDTO;
import com.shopflow.orderservice.dto.OrderResponseDTO;
import com.shopflow.orderservice.exception.OrderNotFoundException;
import com.shopflow.orderservice.exception.OrderValidationException;
import com.shopflow.orderservice.model.Order;
import com.shopflow.orderservice.model.OrderItem;
import com.shopflow.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio de lógica de negocio para pedidos.
 * Calcula totales, valida reglas de negocio y coordina con repositorio.
 */
@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Crea un nuevo pedido calculando el total automáticamente.
     * Regla de negocio: el total mínimo de un pedido es $1.00.
     */
    public OrderResponseDTO createOrder(CreateOrderDTO dto) {
        log.info("Creando pedido para usuario ID: {}", dto.getUserId());

        Order order = new Order();
        order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setUserId(dto.getUserId());
        order.setShippingAddress(dto.getShippingAddress());

        // Mapear ítems y calcular subtotales
        List<OrderItem> items = dto.getItems().stream().map(itemDTO -> {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(itemDTO.getProductId());
            item.setProductName(itemDTO.getProductName());
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getUnitPrice());
            item.setSubtotal(itemDTO.getUnitPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
            return item;
        }).collect(Collectors.toList());

        // Regla de negocio: calcular total
        BigDecimal total = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (total.compareTo(BigDecimal.ONE) < 0) {
            throw new OrderValidationException("El total del pedido debe ser al menos $1.00");
        }

        order.setItems(items);
        order.setTotalAmount(total);

        Order saved = orderRepository.save(order);
        log.info("Pedido creado exitosamente: {}", saved.getOrderNumber());
        return OrderResponseDTO.fromEntity(saved);
    }

    public OrderResponseDTO getOrderById(Long id) {
        log.info("Buscando pedido ID: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido no encontrado con ID: " + id));
        return OrderResponseDTO.fromEntity(order);
    }

    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        log.info("Obteniendo pedidos del usuario ID: {}", userId);
        return orderRepository.findByUserId(userId)
                .stream().map(OrderResponseDTO::fromEntity).collect(Collectors.toList());
    }

    public OrderResponseDTO updateOrderStatus(Long id, String status) {
        log.info("Actualizando estado del pedido ID: {} a {}", id, status);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido no encontrado con ID: " + id));
        try {
            order.setStatus(Order.OrderStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new OrderValidationException("Estado de pedido inválido: " + status);
        }
        order.setUpdatedAt(LocalDateTime.now());
        return OrderResponseDTO.fromEntity(orderRepository.save(order));
    }

    public void cancelOrder(Long id) {
        log.info("Cancelando pedido ID: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Pedido no encontrado: " + id));
        if (order.getStatus() == Order.OrderStatus.SHIPPED || order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new OrderValidationException("No se puede cancelar un pedido ya enviado o entregado");
        }
        order.setStatus(Order.OrderStatus.CANCELLED);
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);
        log.info("Pedido {} cancelado", order.getOrderNumber());
    }
}
