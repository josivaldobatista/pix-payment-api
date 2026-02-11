package com.jfb.pix.payment.exception;

public class PaymentAlreadyPaidException extends RuntimeException {

    public PaymentAlreadyPaidException(String message) {
        super(message);
    }
}
