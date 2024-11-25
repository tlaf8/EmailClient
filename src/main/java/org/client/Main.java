package org.client;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    private final static String db_url = "jdbc:mysql://localhost:3306/AcmePlexUserInfo?useSSL=false&allowPublicKeyRetrieval=true";
    private final static String credential_path = System.getProperty("user.dir") + "/src/main/resources/secret.properties";

    public static void main(String[] args) throws IOException, SQLException {
        EmailClient client = new EmailClient(db_url, credential_path);

        for(String email : client.get_emails()) {
            if (email.contains("fakemail")) {
                System.out.printf("Skipping \033[94m%s\033[0m\n", email);
                continue;
            }
            try {
                if (client.is_on_mailing_list(email)) {
                    client.send_email(email, "Hello from Java", "This is a test email sent using Java");
                }
            } catch (MessagingException msg) {
                System.err.printf("Email failed to send for %s: \n" + msg, email);
            }
        }
    }
}
