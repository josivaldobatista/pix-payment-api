package com.jfb.pix.payment.application.usecase.impl;

import com.jfb.pix.payment.api.dto.CreatePaymentRequest;
import com.jfb.pix.payment.api.dto.PaymentResponse;
import com.jfb.pix.payment.application.usecase.CreatePixPaymentUseCase;
import com.jfb.pix.payment.domain.model.PaymentStatus;
import com.jfb.pix.payment.exception.InvalidQrCodeException;
import com.jfb.pix.payment.exception.PaymentAlreadyPaidException;
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

    private final EmvParser parser = new EmvParser();

    private final PixPaymentJpaRepository pixPaymentJpaRepository;

    @Override
    @Transactional
    public PaymentResponse execute(CreatePaymentRequest request) {

        Map<String, EmvField> fields = parser.parse(request.qrCodePayload());

        BigDecimal amount = extractAmount(fields);

        String txid = extractTxid(fields);

        ensureNotAlreadyPaid(txid);

        PixPaymentEntity saved = savePayment(txid, amount);

        return toResponse(saved);
    }

    private BigDecimal extractAmount(Map<String, EmvField> fields) {
        EmvField amountField = fields.get("54");

        if (amountField == null || amountField.value().isBlank()) {
            return null;
        }

        return new BigDecimal(amountField.value());
    }

    private String extractTxid(Map<String, EmvField> fields) {
        EmvField additionalData = fields.get("62");

        if (additionalData == null || additionalData.value().isBlank()) {
            throw new InvalidQrCodeException(
                    "QR Code inválido: campo Additional Data (62) ausente"
            );
        }

        Map<String, EmvField> subFields = parser.parse(additionalData.value());
        EmvField txidField = subFields.get("05");

        if (txidField == null || txidField.value().isBlank()) {
            throw new InvalidQrCodeException(
                    "QR Code inválido: TXID (62.05) não encontrado"
            );
        }

        return txidField.value();
    }

    private void ensureNotAlreadyPaid(String txid) {
        boolean alreadyPaid =
                pixPaymentJpaRepository.existsByTxidAndStatus(txid, PaymentStatus.PAID);

        if (alreadyPaid) {
            throw new PaymentAlreadyPaidException(
                    "Já existe um pagamento confirmado para este TXID"
            );
        }
    }

    private PixPaymentEntity savePayment(String txid, BigDecimal amount) {
        PixPaymentEntity entity = PixPaymentEntity.builder()
                .id(UUID.randomUUID().toString())
                .txid(txid)
                .amount(amount)
                .status(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        return pixPaymentJpaRepository.save(entity);
    }

    private PaymentResponse toResponse(PixPaymentEntity entity) {
        return new PaymentResponse(
                entity.getId(),
                entity.getTxid(),
                entity.getAmount(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

}
