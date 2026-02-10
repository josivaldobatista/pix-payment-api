package com.jfb.pix.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

    PENDING,
    PAID,
    FAILED,
    CANCELLED;
}
