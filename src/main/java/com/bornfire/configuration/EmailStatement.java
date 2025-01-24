package com.bornfire.configuration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Component;

@Component
public class EmailStatement {

	public String email(String to,String sub, String body) throws  IOException {

		String status;
		System.out.println("given identity");
		String host = "webmail.bornfire.in";
		final String user = "kalaivanan.r@bornfire.in";// change accordingly
		final String password = "Kalai@123";// change accordingly

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			message.setSubject(sub);
			

			BodyPart messageBodyPart = new MimeBodyPart();

			Multipart multipart = new MimeMultipart();
			messageBodyPart.setText(body);
			
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();

			
			multipart.addBodyPart(messageBodyPart);
			Transport.send(message);

			System.out.println("Sent message successfully....");
			status = "success";
		} catch (MessagingException e) {
			throw new RuntimeException(e);

		}
		return status;

	}

}
