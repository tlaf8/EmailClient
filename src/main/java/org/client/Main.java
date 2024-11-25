package org.client;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    private final static String db_url = "jdbc:mysql://localhost:3306/AcmePlexUserInfo?useSSL=false&allowPublicKeyRetrieval=true";
    private final static String credential_path = System.getProperty("user.dir") + "/src/main/resources/secret.properties";

    public static void main(String[] args) throws IOException, SQLException {
        EmailClient client = new EmailClient(db_url, credential_path);

        System.out.println("All emails in database: " + client.get_emails());

        for (String email : client.get_emails()) {
            System.out.printf("%s: Registered? %b Mailing list? %b\n", email, client.is_registered(email), client.is_on_mailing_list(email));
        }
    }
}
