package com.utilities;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class sendMail {
	static final Logger LOGGER = Logger.getLogger(sendMail.class);
	
	public void send(String mailSubject, String msgBody, String mailRecipient) {
		ResourceBundle rb = ResourceBundle.getBundle("com.properties.basicConfiguration");
		final String username = rb.getString("mailUser");
		final String password = rb.getString("mailPassword");
		final String mailServer =  rb.getString("mailServer");
		final String mailServerPort =  rb.getString("mailServerPort");
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", mailServer);
		props.put("mail.smtp.port", mailServerPort);

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(rb.getString("mailUser")));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(mailRecipient));
			message.setSubject(mailSubject);
			message.setText(msgBody);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
