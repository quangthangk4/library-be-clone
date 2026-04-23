package com.library.user.infrastructure.kafka;

import com.library.shared.exception.AppException;
import com.library.shared.exception.ErrorCode;
import com.library.shared.kafka.KafkaTopics;
import com.library.shared.service.EmailService;
import com.library.shared.templates.EmailTemplates;
import com.library.user.application.port.VerificationTokenGenerator;
import com.library.user.domain.entities.User;
import com.library.user.domain.event.UserRegisteredEvent;
import com.library.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRegisteredEventConsumer {

  private final EmailService emailService;
  private final UserRepository userRepository;
  private final VerificationTokenGenerator verificationTokenGenerator;

  @Value("${base.url}")
  private String baseUrl;

  @KafkaListener(topics = KafkaTopics.USER_REGISTERED, groupId = "${spring.kafka.consumer.group-id}")
  public void handle(UserRegisteredEvent event) {
    try {
      User user = userRepository.findById(event.getUserId())
          .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

      String token = verificationTokenGenerator.generateVerifyEmailToken(user);
      String activationLink = baseUrl + "/api/v1/auth/verify-email?token=" + token;

      emailService.sendEmail(event.getEmail(), activationLink, EmailTemplates.VERIFY_EMAIL_TEMPLATE);
      log.info("Sent activation email to {}", event.getEmail());
    } catch (AppException e) {
      log.error("Failed to send activation email to {}: {}", event.getEmail(), e.getMessage());
      throw e;
    } catch (Exception e) {
      log.error("Failed to send activation email to {}: {}", event.getEmail(), e.getMessage());
      throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
  }
}
