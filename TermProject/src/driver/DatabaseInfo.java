package driver;

import entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseInfo {
    private final String databaseURL = "jdbc:mysql://localhost:3306/termproject";
    private User admin;

    public DatabaseInfo() {
        /* just for test */
        this.admin = new User("yeongwoong", "chlduddnd00");
    }
    public Connection makeConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            /* need for establishing connection with DB */
            return DriverManager.getConnection(databaseURL, admin.getUserName(), admin.getPassword());
        } catch (ClassNotFoundException e) {
            System.out.println("Check your JDBC Driver name.");
        } catch (SQLException e) {
            System.out.println("Connection failed");
        }
        return null;
    }
}