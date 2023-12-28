package entity;

import driver.Manageable;

public class Owner implements Manageable {
    @Override
    public void read(String[] row) {

    }

    @Override
    public void print() {

    }

    @Override
    public boolean matches(String [] info) {
        return false;
    }
}
