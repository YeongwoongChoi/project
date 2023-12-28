package driver;

import entity.Customer;
import facade.DataEngineInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CustomerManager implements DataEngineInterface {
    private Connection conn;
    private HashMap <String, String> tableNameToStatement;
    private HashMap <String, String []> tableNameToLabels;
    public static final String ORDER_TABLE = "customerorder";
    public static final String RESERVE_TABLE = "reserve";
    static final String WHERE = " where";
    public static final String RESTAURANT_TABLE = "restaurant";

    CustomerManager() {
        tableNameToStatement = new HashMap<>();
        tableNameToLabels = new HashMap<>();
        conn = new DatabaseInfo().makeConnection();
        tableNameToStatement.put(ORDER_TABLE, "select dishName, totalDishes, dishPrice from customerorder inner join dish on customerorder.dishIdentifier = dish.dishIdentifier where customerIdentifier = ?;");
        tableNameToStatement.put(RESERVE_TABLE, "select restaurant.restaurantCode, restaurantName, reservedTime, numberOfPeople from restaurant inner join reserve on reserve.restaurantCode = restaurant.restaurantCode where customerIdentifier = ?;");
        tableNameToStatement.put(RESTAURANT_TABLE, "select restaurantCode, restaurantName, typeOfDishes, location from restaurant;");

        tableNameToLabels.put(ORDER_TABLE, new String[]{ "Dish Name", "# of Dishes", "Price", "Detailed Info" });
        tableNameToLabels.put(RESERVE_TABLE, new String[]{ "Restaurant Code", "Restaurant Name", "Date and Time", "# of People", "Cancel Reservation" });
        tableNameToLabels.put(RESTAURANT_TABLE, new String[]{ "Restaurant Code", "Restaurant Name", "Dishes It Serves", "Location", "Detailed Info" });
    }

    @Override
    public int getColumnCount(final String tableName) {
        return tableNameToLabels.get(tableName).length;
    }

    @Override
    public String[] getColumnNames(final String tableName) {
        return tableNameToLabels.get(tableName);
    }

    @Override
    public void readAll(String filename) {

    }

    @Override
    public <T> ResultSet search(T m, final String tableName) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            switch (tableName) {
                case ORDER_TABLE:
                    ps = conn.prepareStatement(tableNameToStatement.get(ORDER_TABLE));
                    ps.setString(1, ((Customer)m).getIdentifier());
                    break;
                case RESERVE_TABLE:
                    ps = conn.prepareStatement(tableNameToStatement.get(RESERVE_TABLE));
                    ps.setString(1, ((Customer)m).getIdentifier());
                    break;
                case RESTAURANT_TABLE:
                    ps = conn.prepareStatement(tableNameToStatement.get(RESTAURANT_TABLE));
                    break;
            }
            rs = ps.executeQuery();
        } catch (SQLException e) { e.printStackTrace(); }
        return rs;
    }

    @Override
    public ResultSet searchByKeyword(final String keyword, String tableName) {
        ResultSet rs = null;
        PreparedStatement ps;
        StringBuilder sb = new StringBuilder(tableNameToStatement.get(tableName).split(WHERE)[0]);
        if (sb.charAt(sb.length() - 1) == ';')
            sb.deleteCharAt(sb.length() - 1);

        sb.append(" where typeOfDishes = ");
        try {
            ps = conn.prepareStatement(sb + "?;");
            ps.setString(1, keyword);
            rs = ps.executeQuery();
        } catch (SQLException e) { e.printStackTrace(); }
        return rs;
    }
    @Override
    public <T extends Manageable> void addNewItem(T e, String[] uiTexts) {
        Customer c = (Customer) e;
        try {
            PreparedStatement ps = conn.prepareStatement("insert into reserve values(?, ?, ?, ?);");
            ps.setString(1, c.getIdentifier());
            ps.setString(2, uiTexts[0]);
            ps.setString(3, uiTexts[2]);
            ps.setInt(4, Integer.parseInt(uiTexts[3]));
            ps.executeUpdate();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    @Override
    public <T extends Manageable> void update(T e, String newName, String [] newPhoneNumbers) {
        Customer c = (Customer) e;
        String id = c.getIdentifier();
        var contacts = Main.customerMgr.idToContacts.get(id);
        String [] oldPhoneNumbers = contacts.toArray(String[]::new);

        Map <String, String> oldToNewNumbers =
                IntStream.range(0, Math.min(oldPhoneNumbers.length, newPhoneNumbers.length)).boxed()
                        .collect(Collectors.toMap(i -> oldPhoneNumbers[i], i -> newPhoneNumbers[i]));

        final int oldLength = oldPhoneNumbers.length;
        final int newLength = newPhoneNumbers.length;
        try {
            PreparedStatement ps = conn.prepareStatement("update customer set customerName = ? where customerIdentifier = ?;");
            ps.setString(1, newName);
            ps.setString(2, id);
            ps.executeUpdate();
            c.name = newName;

            for (var entry: oldToNewNumbers.entrySet()) {
                if (entry.getKey().equals(entry.getValue()))
                    continue;
                ps = conn.prepareStatement("update customercontact set phoneNumber = ? where customerIdentifier = (select temp.customerIdentifier from (select * from customercontact as allContacts) as temp where phoneNumber = ?);");
                ps.setString(1, entry.getValue());
                ps.setString(2, entry.getKey());
                ps.executeUpdate();
                contacts.remove(entry.getKey());
                contacts.add(entry.getValue());
            }

            if (oldLength <= newLength) {
                for (int i = oldLength; i < newLength; i++) {
                    ps = conn.prepareStatement("insert ignore into customercontact values(?, ?);");
                    ps.setString(1, id);
                    ps.setString(2, newPhoneNumbers[i]);
                    ps.executeUpdate();
                    contacts.add(newPhoneNumbers[i]);
                }
            }
            else {
                for (int i = oldLength - 1; i >= newLength; i--) {
                    ps = conn.prepareStatement("delete from customercontact where customerIdentifier = ? and phoneNumber = ?;");
                    ps.setString(1, id);
                    ps.setString(2, oldPhoneNumbers[i]);
                    ps.executeUpdate();
                    contacts.remove(oldPhoneNumbers[i]);
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    @Override
    public void remove(String [] info) {
        try {
            PreparedStatement ps = conn.prepareStatement("delete from reserve " +
                    "where customerIdentifier = ? and restaurantCode = ? and reservedTime = ?;");
            for (int i = 0; i < 3; i++)
                ps.setString(i + 1, info[i]);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
