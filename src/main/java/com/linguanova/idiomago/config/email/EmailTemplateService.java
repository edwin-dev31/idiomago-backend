package com.linguanova.idiomago.config.email;

import org.springframework.stereotype.Service;

import static com.linguanova.idiomago.util.AppRoutes.VERIFY_EMAIL_ENDPOINT;

@Service
public class EmailTemplateService {

    public String buildVerificationEmail(String userName, String token) {
		String url = VERIFY_EMAIL_ENDPOINT + token;


        return """
			<html>
			<body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
				<div style="max-width: 600px; margin: auto; background-color: #fff; padding: 30px; border-radius: 8px;">
					<h2 style="color: #2c3e50;">Welcome to IdiomaGo, %s!</h2>
					<p style="font-size: 16px;">Thank you for signing up. To activate your account, please click the button below:</p>
					<p style="text-align: center; margin: 30px 0;">
						<a href="%s" style="background-color: #3498db; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px;">Confirm email</a>
					</p>
					<p style="font-size: 14px; color: #555;">If you did not sign up, you can ignore this email.</p>
				</div>
			</body>
			</html>
		""".formatted(userName, url);
    }
}
