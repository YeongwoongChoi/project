package entity;

import driver.Main;
import driver.Manageable;

import java.util.HashSet;

public class Customer implements Manageable {
    private String identifier;
    public String name;
    public String sex;
    public int age;
    public int earnedPoint;
    @Override
    public void read(String [] row) {
        identifier = row[0];
        name = row[1];
        sex = row[2];
        age = Integer.parseInt(row[3]);
        earnedPoint = Integer.parseInt(row[4]);
    }

    public void setIdentifier(String id) { this.identifier = id; }
    public String getIdentifier() { return this.identifier; }
    @Override
    public void print() {
        System.out.printf("[%2s] %s (%s) age: %d, point: %d\n", identifier, name, sex, age, earnedPoint);
    }
    @Override
    public boolean matches(String identifier, String phoneNumber) {
        HashSet<String> contacts = Main.customerMgr.idToContacts.get(identifier);
        return identifier.equals(this.identifier) && contacts.contains(phoneNumber);
    }
}
