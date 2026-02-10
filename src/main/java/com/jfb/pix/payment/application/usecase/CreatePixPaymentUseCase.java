package com.jfb.pix.payment.application.usecase;

import com.jfb.pix.payment.api.dto.CreatePaymentRequest;
import com.jfb.pix.payment.api.dto.PaymentResponse;

public interface CreatePixPaymentUseCase {

    PaymentResponse execute(CreatePaymentRequest request);
}