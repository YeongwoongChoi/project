package entity;

import driver.Manageable;

import java.io.BufferedReader;
import java.io.IOException;

public class Dish implements Manageable {
    String identifier;
    String dishName;
    double price;

    @Override
    public void read(String [] row) {
        identifier = row[0];
        dishName = row[1];
        price = Double.parseDouble(row[2]);
    }

    @Override
    public void print() {
        System.out.printf("[%s] %s price: %f\n", identifier, dishName, price);
    }

    @Override
    public boolean matches(String name, String phoneNumber) {
        return false;
    }
}
