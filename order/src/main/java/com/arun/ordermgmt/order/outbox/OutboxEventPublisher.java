package com.arun.ordermgmt.order.outbox;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {
    private final OutboxEventRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(OutboxEventPublisher.class);

    @Scheduled(fixedDelay = 5000) // Runs every 5 seconds
    @Transactional
    public void publishEvents() {
        List<OutboxEvent> events = outboxRepository.findByPublishedAtIsNull();
        for (OutboxEvent event : events) {
            try {
                kafkaTemplate.send("order-events", event.getAggregateId(), event.getPayload());
                event.setPublishedAt(Instant.now());
                outboxRepository.save(event);
                log.info("Published outbox event: {}", event.getId());
            } catch (Exception e) {
                log.error("Failed to publish event {}: {}", event.getId(), e.getMessage());
            }
        }
    }
}