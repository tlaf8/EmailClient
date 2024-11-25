package org.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class EmailClient {
    private String email = null;
    private String email_password = null;
    private String db_user = null;
    private String db_password = null;
    private Connection connection = null;

    public EmailClient(String url, String credentials_path) throws RuntimeException, SQLException, IOException {
        read_credentials(credentials_path);
        connect_db(url);
        System.out.printf("Successful connection to database at \033[94m%s\033[0m\n", url);
    }

    public ArrayList<String> get_emails() throws RuntimeException, SQLException {
        ArrayList<String> emails = new ArrayList<>();
        try (ResultSet emails_rs = execute("SELECT email FROM users")) {
            while (emails_rs.next()) {
                emails.add(emails_rs.getString("email"));
            }
        }
        return emails;
    }

    public boolean is_registered(String email) throws RuntimeException, SQLException {
        try (ResultSet registered = execute(String.format("SELECT registered FROM users WHERE email = '%s'", email))) {
            registered.next();
            return registered.getBoolean("registered");
        }
    }

    public boolean is_on_mailing_list(String email) throws RuntimeException, SQLException {
        try (ResultSet mailing_list = execute(String.format("SELECT mailing_list FROM users WHERE email = '%s'", email))) {
            mailing_list.next();
            return mailing_list.getBoolean("mailing_list");
        }
    }

    private ResultSet execute(String sql) throws RuntimeException, SQLException {
        if (connection == null) {
            throw new RuntimeException("Database connection is not set up");
        }
        Statement statement = connection.createStatement();
        return statement.executeQuery(sql);
    }

    private void read_credentials(String path) throws IOException {
        Properties prop = new Properties();
        FileInputStream input = new FileInputStream(path);
        prop.load(input);

        this.email = prop.getProperty("email.sender");
        this.email_password = prop.getProperty("email.password");
        this.db_user = prop.getProperty("db.user");
        this.db_password = prop.getProperty("db.password");
    }

    private void connect_db(String url) throws RuntimeException, SQLException {
        if (db_user == null || db_password == null) {
            throw new RuntimeException("Username or password is not set");
        }
        connection = DriverManager.getConnection(url, db_user, db_password);
    }
}