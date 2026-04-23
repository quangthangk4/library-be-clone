package com.library.user.infrastructure.kafka;

import com.library.shared.kafka.KafkaTopics;
import com.library.user.application.port.UserEventPublisher;
import com.library.user.domain.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventKafkaPublisher implements UserEventPublisher {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Override
  public void publish(UserRegisteredEvent event) {
    kafkaTemplate.send(KafkaTopics.USER_REGISTERED, event.getUserId().getValue().toString(), event);
    log.info("Published UserRegisteredEvent for userId={}", event.getUserId().getValue());
  }
}
