package com.linguanova.idiomago.service.impl;

import com.linguanova.idiomago.config.email.EmailTemplateService;
import com.linguanova.idiomago.config.email.SimpleEmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {

    private final SimpleEmailSender emailSender;
    private final EmailTemplateService emailTemplateService;

    @Autowired
    public EmailVerificationService(SimpleEmailSender emailSender, EmailTemplateService emailTemplateService) {
        this.emailSender = emailSender;
        this.emailTemplateService = emailTemplateService;
    }

    public void sendVerificationEmail(String to, String userName, String token) {
        String subject = "Confirm your account in IdiomaGo";
        String content = emailTemplateService.buildVerificationEmail(userName, token);
        emailSender.sendEmail(to, subject, content);
    }
}
