package com.jfb.pix.payment.api.dto;

import com.jfb.pix.payment.domain.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa o estado atual do pagamento após o processamento inicial.
 */
public record PaymentResponse(
        String paymentId,
        String txid,
        BigDecimal amount,
        PaymentStatus status,
        LocalDateTime createdAt
) {}