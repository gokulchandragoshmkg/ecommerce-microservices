package com.gcg.paymentservice.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.gcg.paymentservice.repository.PaymentRepository;


@Component
public class PaymentEventListener {
	
	@Autowired
	private PaymentRepository paymentRepository;
	

	
}
