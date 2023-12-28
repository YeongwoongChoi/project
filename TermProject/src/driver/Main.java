package driver;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.*;

import entity.Customer;
import entity.Owner;
import entity.Restaurant;
import facade.DataEngineInterface;
import gui.GUIManager;

public class Main {

    public static Manager <Customer> customerMgr;
    public static Manager <Restaurant> restaurantMgr;
    public static Manager <Owner> ownerMgr;

    public void run() {
        setGlobalFont(new Font("Malgun Gothic", Font.PLAIN, 22));

        final String [] types = { "Customer", "Owner" };
        int selected = JOptionPane.showOptionDialog(null, "Select your type. (customer or owner)", "Management System", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, types, types[0]);
        DataEngineInterface engine = null;
        switch (selected) {
            case 0:
                customerMgr = new Manager<>();
                restaurantMgr = new Manager<>();
                customerMgr.readAll("customer", () -> new Customer());
                restaurantMgr.readAll("restaurant", () -> new Restaurant());
                engine = new CustomerManager();
                break;
            case 1:
                ownerMgr = new Manager<>();
                ownerMgr.readAll("owner", () -> new Owner());
                engine = new OwnerManager();
                break;
        }
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