package database;
import java.io.*;
import entity.User;
import java.sql.*;

class DatabaseInfo {
    private final String databaseURL = "jdbc:mysql://localhost:3306/termproject";
    private User admin;

    DatabaseInfo() {
        /* just for test */
        this.admin = new User("yeongwoong", "chlduddnd00");
    }
    String getURL() { return this.databaseURL; }
    String [] getUserInfo() {
        return new String[]{ admin.getUserName(), admin.getPassword() };
    }
}

public class DatabaseTest {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final DatabaseInfo info = new DatabaseInfo();
        String [] userInfo = info.getUserInfo();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            /* need for establishing connection with DB */
            Connection conn = DriverManager.getConnection(info.getURL(), userInfo[0], userInfo[1]);

            /* need for writing query to SQL */
            String sqlStatement = br.readLine();
            if (sqlStatement.isBlank())
                sqlStatement = "select * from vendor";

            PreparedStatement query = conn.prepareStatement(sqlStatement);

            /* need for retrieving the result of query(== PreparedStatement) */
            ResultSet result = query.executeQuery();
            ResultSetMetaData md = result.getMetaData();
            int columns = md.getColumnCount();

            for (int i = 1; i <= columns; i++)
                System.out.printf("%3s ", md.getColumnName(i));
            System.out.println();
            while (result.next()) {
                for (int i = 1; i <= columns; i++)
                    System.out.printf("%3s ", result.getString(i));
                System.out.println();
            }

        } catch (ClassNotFoundException e) {
            System.out.println("Check your JDBC Driver name.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}