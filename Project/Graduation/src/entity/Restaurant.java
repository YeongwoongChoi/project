package entity;

import utils.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Restaurant {
    private String restaurantCode;
    private String typeOfDishes;
    private String restaurantName;
    private String location;
    private double rating;
    private String phoneNumber;

    public Restaurant(String[] row) {
        restaurantCode = row[0];
        typeOfDishes = row[1];
        restaurantName = row[2];
        location = row[3];
        rating = Double.parseDouble(row[4]);
        phoneNumber = row[5];
    }

    public String getHTMLParsedName() {
        return DataBaseUtil.replaceSpace(restaurantName.replaceAll("&", "and"));
    }

    public String getRestaurantCode() { return restaurantCode; }
    public String getTypeOfDishes() { return typeOfDishes; }
    public String getRestaurantName() { return restaurantName; }
    public String getLocation() { return location; }
    public double getRating() { return rating; }
    public String getPhoneNumber() { return phoneNumber; }

    public ResultSet getMenus() {
        try {
            Class.forName(DataBaseUtil.getDatabaseDriver());
            Connection conn = DataBaseUtil.getConnection();
            String sql = "select dishIdentifier, dishName, dishPrice from dish join serve using (dishIdentifier) where restaurantCode=?;";
            PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setString(1, restaurantCode);
            return ps.executeQuery();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getStackTrace()[0]);
        }
        return null;
    }
    public String getDescription() {
        return String.format("%s whose rating is %.2f serves %s foods and locates at %s. Contact %s\n",
                restaurantName, rating, typeOfDishes, location, phoneNumber);
    }

    public void setRating(final double rating) { this.rating = rating; }
    public boolean updateRating(final double userRating) {
        try {
            Class.forName(DataBaseUtil.getDatabaseDriver());
            Connection conn = DataBaseUtil.getConnection();
            String sql = "select count(*) from review where restaurantCode = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, restaurantCode);
            ResultSet rs = ps.executeQuery(); rs.next();
            int reviews = rs.getInt(1);
            if (reviews == 0) {
                rating += userRating;
                rating /= 2.0d;
            }
            else {
                sql = "select rating from review where restaurantCode = ?;";
                ps = conn.prepareStatement(sql);
                ps.setString(1, restaurantCode);
                rs = ps.executeQuery();
                while (rs.next())
                    rating += rs.getDouble(1);
                rating /= (reviews + 1);
            }

            sql = "update restaurant set rating = ? where restaurantCode = ?;";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, rating);
            ps.setString(2, restaurantCode);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getStackTrace()[0]);
        }
        return false;
    }
}