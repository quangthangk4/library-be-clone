package com.library.shared.service;

import com.library.shared.templates.EmailTemplates;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

  void sendEmail(String to, String fullName, String link, EmailTemplates emailTemplates)
      throws MessagingException, UnsupportedEncodingException;
}
