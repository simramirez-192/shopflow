package com.shopflow.paymentservice.controller;
import com.shopflow.paymentservice.dto.CreatePaymentDTO;
import com.shopflow.paymentservice.dto.PaymentResponseDTO;
import com.shopflow.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) { this.paymentService = paymentService; }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> process(@Valid @RequestBody CreatePaymentDTO dto) {
        log.info("POST /api/payments");
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.processPayment(dto));
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getByOrderId(orderId));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponseDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.getByUserId(userId));
    }
}
