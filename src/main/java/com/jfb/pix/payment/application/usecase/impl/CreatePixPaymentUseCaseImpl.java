package com.jfb.pix.payment.application.usecase.impl;

import com.jfb.pix.payment.api.dto.CreatePaymentRequest;
import com.jfb.pix.payment.api.dto.PaymentResponse;
import com.jfb.pix.payment.application.usecase.CreatePixPaymentUseCase;
import com.jfb.pix.payment.domain.model.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreatePixPaymentUseCaseImpl implements CreatePixPaymentUseCase {

    // No futuro, aqui entrarão as dependências de:
    // - PixQrCodeParser (Infrastructure)
    // - PixPaymentJpaRepository (Infrastructure)

    @Override
    public PaymentResponse execute(CreatePaymentRequest request) {
        // 1. Recebe o payload do QR Code [cite: 9]
        String payload = request.qrCodePayload();

        // 2. [MOCK] Por enquanto, simulamos a criação sem o parser real
        // O objetivo é validar o fluxo Controller -> UseCase
        return new PaymentResponse(
                UUID.randomUUID().toString(),
                "TXID123456",            // Virá da tag 62 futuramente [cite: 16]
                null,                    // Valor virá da tag 54 [cite: 16]
                PaymentStatus.PENDING, // Pagamento inicia pendente [cite: 63]
                LocalDateTime.now()
        );
    }
}