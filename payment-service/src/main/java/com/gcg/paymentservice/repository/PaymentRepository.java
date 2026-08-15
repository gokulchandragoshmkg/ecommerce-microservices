package com.gcg.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gcg.paymentservice.entity.PaymentDetail;

public interface PaymentRepository extends JpaRepository<PaymentDetail, Long> {

}
