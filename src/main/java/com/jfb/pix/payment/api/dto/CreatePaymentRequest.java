package com.jfb.pix.payment.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePaymentRequest(
        @NotBlank(message = "O payload do QR Code é obrigatório")
        String qrCodePayload
) {}