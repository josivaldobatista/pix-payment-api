package com.jfb.pix.payment.api.controller;

import com.jfb.pix.payment.domain.model.PaymentStatus;
import com.jfb.pix.payment.infrastructure.persistence.entity.PixPaymentEntity;
import com.jfb.pix.payment.infrastructure.persistence.repository.PixPaymentJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/pix/simulator")
public class PixPaymentSimulatorController {

    private final PixPaymentJpaRepository paymentRepository;

    public PixPaymentSimulatorController(PixPaymentJpaRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    @PostMapping("/confirm/{paymentId}")
    public ResponseEntity<?> confirmPayment(@PathVariable String paymentId) {

        PixPaymentEntity payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Pagamento não encontrado"
                ));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Pagamento não está pendente"
            );
        }

        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidAt(LocalDateTime.now());

        paymentRepository.save(payment);

        return ResponseEntity.ok(payment);
    }
}

