package com.jfb.pix.payment.exception;

public class InvalidQrCodeException extends RuntimeException {

    public InvalidQrCodeException(String message) {
        super(message);
    }
}
