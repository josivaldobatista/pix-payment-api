package com.jfb.pix.payment.infrastructure.persistence.entity;

import com.jfb.pix.payment.domain.model.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "pix_payment_db")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PixPaymentEntity {

    @Id
    private String id; // UUID gerado na aplicação

    @Column(nullable = false)
    private String txid;

    @Column(precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}