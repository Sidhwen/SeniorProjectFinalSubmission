/**
*Class used to send email to user when they attempt to login from a different WLAN.
*Jake Strojny
*
*/

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Mailer {
	Mailer(){

	}

	public static void send(String fromAddr, String password, String toAddr, String subject, String emailBody) {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");		
Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(fromAddr, password);
		}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromAddr));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));
			message.setSubject(subject);
			message.setText(emailBody);
			Transport.send(message);
		} catch(MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
