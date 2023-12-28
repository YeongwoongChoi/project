package entity;

import driver.Main;
import driver.Manageable;
import facade.UIData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Customer implements Manageable, UIData {
    private String identifier;
    public String name;
    public String sex;
    public int age;
    public int earnedPoint;
    public ArrayList<String []> reservationList = new ArrayList<>();

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
    public boolean matches(String [] info) {
        var contacts = Main.customerMgr.idToContacts.get(info[0]);
        return info[0].equals(this.identifier) && contacts.contains(info[1]);
    }

    @Override
    public void set(Object[] rawType) {

    }

    @Override
    public String[] getTexts() {
        //return new String[] { identifier, name, sex, String.valueOf(age), String.valueOf(earnedPoint) };
        return null;
    }
}
