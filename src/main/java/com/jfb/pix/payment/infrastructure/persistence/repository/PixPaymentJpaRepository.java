package com.jfb.pix.payment.infrastructure.persistence.repository;

import com.jfb.pix.payment.domain.model.PaymentStatus;
import com.jfb.pix.payment.infrastructure.persistence.entity.PixPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PixPaymentJpaRepository extends JpaRepository<PixPaymentEntity, String> {

    boolean existsByTxidAndStatus(String txid, PaymentStatus status);

}