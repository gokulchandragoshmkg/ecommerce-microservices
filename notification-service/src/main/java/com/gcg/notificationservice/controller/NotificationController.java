package com.gcg.notificationservice.controller;

import com.gcg.notificationservice.entity.NotificationLog;
import com.gcg.notificationservice.repository.NotificationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/notificationservice")
public class NotificationController {

    @Autowired
    private NotificationLogRepository notificationLogRepository;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<NotificationLog>> getByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(notificationLogRepository.findAll().stream()
                .filter(n -> n.getOrderId().equals(orderId))
                .toList());
    }
}