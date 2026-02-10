package com.jfb.pix.payment.infrastructure.qrcode.emv;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EmvParser {

    public Map<String, EmvField> parse(String payload) {
        if (payload == null || payload.isBlank()) {
            return Collections.emptyMap();
        }

        Map<String, EmvField> fields = new HashMap<>();
        int index = 0;

        try {
            while (index < payload.length() - 4) { // -4 para ignorar o CRC final por enquanto [cite: 16]
                String tag = payload.substring(index, index + 2);
                String lengthStr = payload.substring(index + 2, index + 4);
                int length = Integer.parseInt(lengthStr);

                String value = payload.substring(index + 4, index + 4 + length);

                fields.put(tag, new EmvField(tag, lengthStr, value));

                index += 4 + length;
            }
        } catch (Exception e) {
            // No futuro, lançaremos uma InvalidQrCodeException customizada [cite: 95]
            return fields;
        }

        return fields;
    }
}