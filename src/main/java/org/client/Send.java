package org.client;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.IOException;

public class Send {
    public static void main(String[] args) {
        Properties secrets = new Properties();
        try (FileInputStream in = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/secret.properties")) {
            secrets.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String email = secrets.getProperty("email");
        final String password = secrets.getProperty("password");

        ArrayList<String> mailing_list = new ArrayList<>();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 587);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            for (String s : mailing_list) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(s));
                message.setSubject("Test email");
                message.setText("Hello!");
                Transport.send(message);
                System.out.printf("Sent message to \033[94m%s\033[0m", s);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}