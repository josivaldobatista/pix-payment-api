package com.jfb.pix.payment.infrastructure.persistence.repository;

import com.jfb.pix.payment.infrastructure.persistence.entity.PixPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PixPaymentJpaRepository extends JpaRepository<PixPaymentEntity, String> {
}