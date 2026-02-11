package com.jfb.pix.payment.application.usecase.impl;

import com.jfb.pix.payment.api.dto.CreatePaymentRequest;
import com.jfb.pix.payment.api.dto.PaymentResponse;
import com.jfb.pix.payment.application.usecase.CreatePixPaymentUseCase;
import com.jfb.pix.payment.domain.model.PaymentStatus;
import com.jfb.pix.payment.infrastructure.persistence.entity.PixPaymentEntity;
import com.jfb.pix.payment.infrastructure.persistence.repository.PixPaymentJpaRepository;
import com.jfb.pix.payment.infrastructure.qrcode.emv.EmvField;
import com.jfb.pix.payment.infrastructure.qrcode.emv.EmvParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreatePixPaymentUseCaseImpl implements CreatePixPaymentUseCase {

    // Por enquanto instanciado diretamente.
    // Em breve, evoluímos para injeção via @Bean
    private final EmvParser parser = new EmvParser();

    private final PixPaymentJpaRepository pixPaymentJpaRepository;

    @Override
    @Transactional
    public PaymentResponse execute(CreatePaymentRequest request) {

        // 1. Parse do payload EMV completo
        Map<String, EmvField> fields = parser.parse(request.qrCodePayload());

        // 2. Extração do valor (Tag 54) - opcional no Pix
        BigDecimal amount = null;
        EmvField amountField = fields.get("54");
        if (amountField != null && !amountField.value().isBlank()) {
            amount = new BigDecimal(amountField.value());
        }

        // 3. Extração correta do TXID (Tag 62 -> Sub-tag 05)
        String txid = "NOT_FOUND";
        EmvField additionalDataField = fields.get("62");

        if (additionalDataField != null) {
            // A Tag 62 também segue o padrão TLV
            Map<String, EmvField> subFields62 = parser.parse(additionalDataField.value());
            EmvField txidField = subFields62.get("05");

            if (txidField != null && !txidField.value().isBlank()) {
                txid = txidField.value();
            }
        }

        // 4. Persistência do pagamento
        PixPaymentEntity entity = PixPaymentEntity.builder()
                .id(UUID.randomUUID().toString())
                .txid(txid)
                .amount(amount)
                .status(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        PixPaymentEntity saved = pixPaymentJpaRepository.save(entity);

        // 5. Resposta da API
        return new PaymentResponse(
                saved.getId(),
                saved.getTxid(),
                saved.getAmount(),
                saved.getStatus(),
                saved.getCreatedAt()
        );
    }
}
