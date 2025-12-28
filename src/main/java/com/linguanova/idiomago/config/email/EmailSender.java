package com.linguanova.idiomago.config.email;

public interface EmailSender {
	void sendEmail(String to, String subject, String content);
}
