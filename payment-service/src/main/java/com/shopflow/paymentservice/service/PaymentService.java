package com.shopflow.paymentservice.service;

import com.shopflow.paymentservice.dto.CreatePaymentDTO;
import com.shopflow.paymentservice.dto.PaymentResponseDTO;
import com.shopflow.paymentservice.exception.PaymentException;
import com.shopflow.paymentservice.model.Payment;
import com.shopflow.paymentservice.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio de pagos.
 * Regla de negocio: un pedido solo puede tener un pago activo.
 */
@Service
public class PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) { this.paymentRepository = paymentRepository; }

    public PaymentResponseDTO processPayment(CreatePaymentDTO dto) {
        log.info("Procesando pago para pedido ID: {}", dto.getOrderId());
        if (paymentRepository.existsByOrderId(dto.getOrderId())) {
            throw new PaymentException("Ya existe un pago para el pedido: " + dto.getOrderId());
        }

        Payment payment = new Payment();
        payment.setOrderId(dto.getOrderId());
        payment.setUserId(dto.getUserId());
        payment.setAmount(dto.getAmount());
        try {
            payment.setMethod(Payment.PaymentMethod.valueOf(dto.getMethod().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new PaymentException("Método de pago inválido: " + dto.getMethod());
        }
        payment.setStatus(Payment.PaymentStatus.PROCESSING);
        payment.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase());
        payment.setProcessedAt(LocalDateTime.now());
        payment.setStatus(Payment.PaymentStatus.COMPLETED);

        Payment saved = paymentRepository.save(payment);
        log.info("Pago completado. Transacción: {}", saved.getTransactionId());
        return PaymentResponseDTO.fromEntity(saved);
    }

    public PaymentResponseDTO getByOrderId(Long orderId) {
        return PaymentResponseDTO.fromEntity(paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentException("Pago no encontrado para pedido: " + orderId)));
    }

    public List<PaymentResponseDTO> getByUserId(Long userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(PaymentResponseDTO::fromEntity).collect(Collectors.toList());
    }
}
