package com.jfb.pix.payment.api.controller;


import com.jfb.pix.payment.api.dto.CreatePaymentRequest;
import com.jfb.pix.payment.api.dto.PaymentResponse;
import com.jfb.pix.payment.application.usecase.CreatePixPaymentUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/pix/payments")
@RequiredArgsConstructor
public class PixPaymentController {

    private final CreatePixPaymentUseCase createPixPaymentUseCase;

    @PostMapping
    public ResponseEntity<PaymentResponse> create(@RequestBody @Valid CreatePaymentRequest request) {
        // O Use Case processa o Record de entrada e devolve o Record de resposta
        PaymentResponse response = createPixPaymentUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}