package entity;

import driver.Manageable;

import java.io.BufferedReader;
import java.io.IOException;

public class Restaurant implements Manageable {
    String restaurantCode;
    String typeOfDishes;
    String restaurantName;
    String location;
    double rating;
    String phoneNumber;
    @Override
    public void read(String [] row) {
        restaurantCode = row[0];
        typeOfDishes = row[1];
        restaurantName = row[2];
        location = row[3];
        rating = Double.parseDouble(row[4]);
        phoneNumber = row[5];
    }

    @Override
    public void print() {
        System.out.printf("[%s] %s, which rating is %f serves %s and locates at %s. Contact %s\n",
                restaurantCode, restaurantName, rating, typeOfDishes, location, phoneNumber);
    }

    @Override
    public boolean matches(String name, String phoneNumber) {
        return false;
    }

}
