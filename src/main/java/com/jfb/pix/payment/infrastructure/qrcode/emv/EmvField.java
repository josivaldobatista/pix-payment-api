package com.jfb.pix.payment.infrastructure.qrcode.emv;

public record EmvField(String tag, String length, String value) {}