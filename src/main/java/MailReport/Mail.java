package MailReport;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	public static void sendMessage(String subject, String text, String destinataire)
			throws AddressException, MessagingException {
		String smtpHost = "smtp.gmail.com";
		String from = "jelfakawy@gmail.com";
		String to = "jelfakawy@gmail.com";
		String username = "jelfakawy@gmail.com";
		String password = "Fifa3394";

		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject("Hello");
		message.setText("Hello World");

		Transport tr = session.getTransport("smtp");
		tr.connect(smtpHost, username, password);
		message.saveChanges();

		// tr.send(message);
		/**
		 * Genere l'erreur. Avec l authentification, oblige d utiliser
		 * sendMessage meme pour une seule adresse...
		 */

		tr.sendMessage(message, message.getAllRecipients());
		tr.close();

	}
}