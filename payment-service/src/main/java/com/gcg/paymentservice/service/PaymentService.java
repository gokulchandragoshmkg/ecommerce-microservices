package com.gcg.paymentservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.gcg.paymentservice.entity.PaymentDetail;
import com.gcg.paymentservice.entity.PaymentRequest;
import com.gcg.paymentservice.event.PaymentCompletedEvent;
import com.gcg.paymentservice.event.PaymentFailedEvent;
import com.gcg.paymentservice.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public PaymentDetail processPayment(PaymentRequest request) {
		boolean success = true; // mock payment gateway logic

		PaymentDetail payment = new PaymentDetail();
		payment.setOrderId(request.getOrderId());
		payment.setProductId(request.getProductId());
		payment.setPaidAmount(request.getQuantity() * 100.0f);
		payment.setPaymentStatus(success ? "SUCCESS" : "FAILED");
		payment.setPaymentMethod("CARD");
		payment.setBankName(request.getBankName());
		
		 PaymentDetail saved = paymentRepository.save(payment);

		    try {
		        if (success) {
		            PaymentCompletedEvent event = new PaymentCompletedEvent();
		            event.setOrderId(request.getOrderId());
		            kafkaTemplate.send("payment-completed", event).get(); // .get() blocks until Kafka confirms
		        } else {
		            PaymentFailedEvent event = new PaymentFailedEvent();
		            event.setOrderId(request.getOrderId());
		            kafkaTemplate.send("payment-failed", event).get();
		        }
		    } catch (Exception e) {
		        // Kafka send genuinely failed — decide what "no use of sending the details" means for you
		        throw new RuntimeException("Payment recorded but event publish failed: " + e.getMessage(), e);
		    }

		    return saved;
	}
	
	public List<PaymentDetail> findByOrderId(Long orderId){
		 return paymentRepository.findByOrderId(orderId);
	}
}
