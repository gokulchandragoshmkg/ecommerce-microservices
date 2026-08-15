package com.gcg.paymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Payment_detail")
@Getter
@Setter
public class PaymentDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long productId;
	private Long userId;
	private String paymentStatus;
	private float paidAmount;
	private String paymentMethod;
	private String bankName;
	
}
