package driver;

import entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Manager <T extends Manageable> {
    public ArrayList <T> entityList = new ArrayList<>();
    public HashMap <String, HashSet <String>> idToContacts = new HashMap<>();
    final String SEPARATOR = "=";
    public void readAll(final String entityName, Factory <T> f) {
        DatabaseInfo info = new DatabaseInfo();

        try {
            Connection conn = info.makeConnection();
            /* need for writing query to SQL */
            PreparedStatement queryForEntities = conn.prepareStatement("select * from " + entityName);
            ResultSet entitySet = queryForEntities.executeQuery();
            ResultSetMetaData md = entitySet.getMetaData();
            int columns = md.getColumnCount();
            String [] row;

            while (entitySet.next()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i <= columns; i++)
                    sb.append(entitySet.getString(i)).append(SEPARATOR);
                row = sb.toString().stripTrailing().split(SEPARATOR);
                T entity = f.create();
                entity.read(row);
                entityList.add(entity);

                if (entityName.equals("customer"))
                    readContacts(conn, row[0]);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get the table information.");
        }
    }

    private void readContacts(Connection conn, String id) {
        try {
            PreparedStatement queryForContacts = conn.prepareStatement("select phoneNumber from customercontact where customerIdentifier = ?");
            queryForContacts.setString(1, id);
            ResultSet queriedContacts = queryForContacts.executeQuery();

            if (queriedContacts != null) {
                HashSet<String> contacts = idToContacts.get(id);
                if (contacts == null) {
                    contacts = new HashSet<>();
                    idToContacts.put(id, contacts);
                }
                while (queriedContacts.next())
                    contacts.add(queriedContacts.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("SQL statement error");
        }
    }

    public void printAll() { entityList.forEach(T::print); }

    public T find(String id, String phoneNumber) {
        for (T e: entityList) {
            if (e.matches(id, phoneNumber))
                return e;
        }
        return null;
    }

    public List<T> findAll(String name, String phoneNumber) {
        return entityList.stream().filter(e -> e.matches(name, phoneNumber)).collect(Collectors.toList());
    }

    public void addEntity(T e) {
        entityList.add(e);
        e.print();
    }

}
