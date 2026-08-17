package com.gcg.orderservice.outbox;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcg.orderservice.entity.OutboxEvent;
import com.gcg.orderservice.event.OrderCreatedEvent;
import com.gcg.orderservice.repository.OutboxEventRepository;

@Component
public class OutboxPoller {

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Scheduled(fixedDelay = 2000)
    public void pollAndPublish() {
        List<OutboxEvent> unpublished = outboxEventRepository.findByPublishedFalse();

        for (OutboxEvent event : unpublished) {
            try {
                if ("orderCreated".equals(event.getEventType())) {
                    OrderCreatedEvent typedEvent = objectMapper.readValue(event.getPayload(), OrderCreatedEvent.class);
                    kafkaTemplate.send("order-created", typedEvent).get();
                }
                event.setPublished(true);
                outboxEventRepository.save(event);
            } catch (Exception e) {
                System.err.println("Failed to publish outbox event " + event.getId() + ": " + e.getMessage());
            }
        }
    }
}