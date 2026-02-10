package com.jfb.pix.payment.application.usecase.impl;

import com.jfb.pix.payment.api.dto.CreatePaymentRequest;
import com.jfb.pix.payment.api.dto.PaymentResponse;
import com.jfb.pix.payment.application.usecase.CreatePixPaymentUseCase;
import com.jfb.pix.payment.domain.model.PaymentStatus;
import com.jfb.pix.payment.infrastructure.qrcode.emv.EmvField;
import com.jfb.pix.payment.infrastructure.qrcode.emv.EmvParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreatePixPaymentUseCaseImpl implements CreatePixPaymentUseCase {

    private final EmvParser parser = new EmvParser(); // Evoluiremos para injeção via Config [cite: 90]

    @Override
    public PaymentResponse execute(CreatePaymentRequest request) {
        Map<String, EmvField> fields = parser.parse(request.qrCodePayload());

        // Extraindo dados reais conforme o padrão Pix [cite: 16]
        String txid = fields.containsKey("62") ? fields.get("62").value() : "NOT_FOUND";

        BigDecimal amount = null;
        if (fields.containsKey("54")) {
            amount = new BigDecimal(fields.get("54").value());
        }

        return new PaymentResponse(
                UUID.randomUUID().toString(),
                txid,
                amount,
                PaymentStatus.PENDING,
                LocalDateTime.now()
        );
    }
}