package com.gcg.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "notification-service")
public interface NotificationClient {
    @GetMapping("/v1/notificationservice/order/{orderId}")
    List<Map<String, Object>> getNotificationsByOrder(@PathVariable Long orderId);
}