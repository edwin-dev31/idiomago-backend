package com.linguanova.idiomago.config.email;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import org.springframework.stereotype.Component;

import java.util.Properties;
@Component
public abstract class AbstractEmailSender implements EmailSender {
	protected String username;
	protected String password;
	protected final Session session;

	public AbstractEmailSender(String username, String password) {
		this.username = username;
		this.password = password;
		this.session = createSession();
	}

	private Session createSession( ) {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		return Session.getInstance(properties, new SmtpAuthenticator(username, password));
	}

	protected void sendMessage(Message message) throws Exception {
		Transport.send(message);
		System.out.println("Correo enviado con éxito!");
	}
}
