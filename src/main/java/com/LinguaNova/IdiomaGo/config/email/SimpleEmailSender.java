package com.LinguaNova.IdiomaGo.config.email;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleEmailSender extends AbstractEmailSender {

	public SimpleEmailSender(
			@Value("${mail.username}") String username,
			@Value("${mail.password}") String password
	) {
		super(username, password);
	}

	@Override
	public void sendEmail(String to, String subject, String content) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setContent(content, "text/html; charset=utf-8");
			sendMessage(message);
		} catch (Exception e) {
            log.error("Error sending email to: {}", to, e);
		}
	}

}
