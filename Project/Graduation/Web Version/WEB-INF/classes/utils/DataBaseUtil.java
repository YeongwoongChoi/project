package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

public class DataBaseUtil {
	private static final String DATABASE_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String DATABASE_URL = "jdbc:mariadb://34.83.133.206:3306/termproject";
	private static final String DATABASE_USER_ID = "root";
	private static final String DATABASE_USER_PASSWORD = "YOUR_PASSWORD";
	private static final String FILE_UPLOAD_PATH = "/var/lib/tomcat9/webapps/ROOT/uploaded";
    private static final char [] PASSWORD_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
                                                        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                                                        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                                                        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                                                        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                                                        '@', '$', '!', '%', '*', '?', '&'};

	public static final int RESERVE_VIEW_DEFAULT = 0;
	public static final int RESERVE_VIEW_RATING = 1;
	public static final int RESERVE_VIEW_VISITORS = 2;
	public static final int RESERVE_VIEW_DISHES = 3;
	public static final int MAX_UPLOAD_SIZE = 10 * 1024 * 1024;
	public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final int REVIEW_POINT = 100;

	public static String getDatabaseDriver() { return DATABASE_DRIVER; }
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DATABASE_URL, DATABASE_USER_ID, DATABASE_USER_PASSWORD);
	}

	public static String getLocalizedSexName(final String sex) {
		return sex.contentEquals("Male") ? "남자": "여자";
	}
	public static String replaceSpace(final String s) { return s.replaceAll(" ", "\\u00A0"); }
	public static String getDateTimeFormat(final String dateTime) {
		return LocalDateTime.parse(dateTime, DATETIME_FORMATTER).format(DATETIME_FORMATTER);
	}
	public static String getDateTimeFormat(final String date, final String time) {
		LocalDate d = LocalDate.parse(date);
		LocalTime t = LocalTime.parse(time);
		return LocalDateTime.of(d, t).format(DATETIME_FORMATTER);
	}
	public static String getFileUploadPath() { return FILE_UPLOAD_PATH; }

    public static String getInitializedPassword() {
        SecureRandom sr = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        final int candidates = PASSWORD_CHARS.length;
        for (int i = 0; i < 8; i++)
            sb.append(PASSWORD_CHARS[sr.nextInt(candidates)]);

        return sb.toString();
    }

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
