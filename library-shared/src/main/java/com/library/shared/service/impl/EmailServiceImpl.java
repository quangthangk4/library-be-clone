package com.library.shared.service.impl;

import com.library.shared.service.EmailService;
import com.library.shared.templates.EmailTemplates;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  @Value("${spring.mail.username}")
  private String from;

  @Override
  public void sendEmail(String to, String link, EmailTemplates emailTemplates)
      throws MessagingException, UnsupportedEncodingException {
    log.info("Sending email verification to {}", to);
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
    if (to.contains(",")) {
      messageHelper.setTo(InternetAddress.parse(to));
    } else {
      messageHelper.setTo(to);
    }
    messageHelper.setFrom(from, "Library Management System");
    messageHelper.setSubject(emailTemplates.getSubject());

    String content = emailTemplates.formatContent(to, link);
    messageHelper.setText(content, true);
    mailSender.send(mimeMessage);
    log.info("Sending email to {} with subject: {}", to, emailTemplates.getSubject());
  }
}
