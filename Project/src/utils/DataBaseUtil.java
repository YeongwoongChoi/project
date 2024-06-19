package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseUtil {
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/your directory";
    private static final String DATABASE_USER_ID = "yeongwoong";
    private static final String DATABASE_USER_PASSWORD = "your password";
    private static final String FILE_UPLOAD_PATH = "D:\\Graduate\\web\\uploaded";

    public static final int RESERVE_VIEW_DEFAULT = 0;
    public static final int RESERVE_VIEW_RATING = 1;
    public static final int RESERVE_VIEW_VISITORS = 2;
    public static final int RESERVE_VIEW_DISHES = 3;
    public static final int MAX_UPLOAD_SIZE = 10 * 1024 * 1024;

    public static final int REVIEW_POINT = 100;

    public static String getDatabaseDriver() { return DATABASE_DRIVER; }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER_ID, DATABASE_USER_PASSWORD);
    }

    public static String getLocalizedSexName(final String sex) {
        return sex.contentEquals("Male") ? "남자": "여자";
    }
    public static String replaceSpace(final String s) { return s.replaceAll(" ", "&nbsp;"); }
    public static String getDateTimeFormat(final String date, final String time) { return date + " " + time; }
    public static String getFileUploadPath() { return FILE_UPLOAD_PATH; }
    public static String getHashedValue(final String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(s.getBytes());

            StringBuilder hex = new StringBuilder();
            for (byte b: hashed)
                hex.append(String.format("%02x", b));
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            return String.valueOf(e.getStackTrace()[0]);
        }
    }
}