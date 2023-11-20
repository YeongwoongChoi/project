package driver;
import java.awt.Font;
import java.util.Enumeration;
import java.util.Scanner;

import javax.swing.UIManager;

import entity.Customer;
import entity.Restaurant;
import gui.GUIManager;

public class Main {

    public static Manager <Customer> customerMgr = new Manager <>();
    //public static Manager <Restaurant> restaurantManager = new Manager <>();

    public void run() {
        setGlobalFont(new Font("Malgun Gothic", Font.PLAIN, 22));

        customerMgr.readAll("customer", () -> new Customer());
        customerMgr.printAll();
        CustomerManager engine = new CustomerManager();
        GUIManager.run(engine);
    }

    public static void setGlobalFont(Font font) {
        Enumeration <Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof Font) {
                UIManager.put(key, font);
            }
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}