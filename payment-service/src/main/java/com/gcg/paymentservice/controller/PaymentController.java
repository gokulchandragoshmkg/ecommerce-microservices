package com.gcg.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gcg.paymentservice.entity.PaymentDetail;
import com.gcg.paymentservice.entity.PaymentRequest;
import com.gcg.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/v1/paymentservice")
public class PaymentController {
	
	@Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<PaymentDetail> makePayment(@RequestBody PaymentRequest request) {
    	PaymentDetail p = paymentService.processPayment(request);
        return ResponseEntity.ok(p);
    }
}
