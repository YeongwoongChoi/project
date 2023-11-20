package entity;

import driver.Manageable;

import java.util.HashMap;

public class Vendor implements Manageable {
    String identifier;
    String name;
    HashMap<String, Double> ingredientsToPrice;

    @Override
    public void read(String [] row) {

    }

    @Override
    public void print() {

    }

    @Override
    public boolean matches(String name, String phoneNumber) {
        return false;
    }
}
